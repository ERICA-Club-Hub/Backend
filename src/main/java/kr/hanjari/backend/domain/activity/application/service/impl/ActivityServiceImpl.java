package kr.hanjari.backend.domain.activity.application.service.impl;

import static kr.hanjari.backend.domain.activity.presentation.dto.response.RecentActivityLogResponse.RecentActivityLog.of;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

import kr.hanjari.backend.domain.activity.application.service.ActivityImageService;
import kr.hanjari.backend.domain.activity.domain.entity.Activity;
import kr.hanjari.backend.domain.activity.presentation.dto.response.ActivityCommandResponse;
import kr.hanjari.backend.domain.club.application.query.ClubQueryService;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.file.application.FileService;
import kr.hanjari.backend.domain.file.domain.dto.FileDownloadDTO;
import kr.hanjari.backend.domain.file.domain.entity.File;
import kr.hanjari.backend.domain.activity.domain.entity.ActivityImageId;
import kr.hanjari.backend.domain.activity.domain.entity.ActivityImage;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.domain.activity.domain.repository.ActivityImageRepository;
import kr.hanjari.backend.domain.activity.domain.repository.ActivityRepository;
import kr.hanjari.backend.domain.activity.application.service.ActivityService;
import kr.hanjari.backend.infrastructure.s3.S3Service;
import kr.hanjari.backend.domain.activity.presentation.dto.request.CreateActivityRequest;
import kr.hanjari.backend.domain.activity.presentation.dto.request.UpdateActivityRequest;
import kr.hanjari.backend.domain.activity.presentation.dto.ActivityImageDTO;
import kr.hanjari.backend.domain.activity.presentation.dto.ActivityThumbnailDTO;
import kr.hanjari.backend.domain.activity.presentation.dto.response.GetAllActivityResponse;
import kr.hanjari.backend.domain.activity.presentation.dto.response.GetSpecificActivityResponse;
import kr.hanjari.backend.domain.activity.presentation.dto.response.RecentActivityLogResponse;
import kr.hanjari.backend.domain.activity.presentation.dto.response.RecentActivityLogResponse.RecentActivityLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private static final int MAX_RECENT_ACTIVITY = 4;

    private final ActivityRepository activityRepository;
    private final ActivityImageRepository activityImageRepository;

    private final ActivityImageService activityImageService;
    private final FileService fileService;
    private final S3Service s3Service;
    private final ClubQueryService clubQueryService;

    @Override
    public ActivityCommandResponse createActivity(Long clubId, CreateActivityRequest createActivityRequest, List<MultipartFile> images) {

        Activity activity = createActivityRequest.toEntity();
        Club club = clubQueryService.getReference(clubId);

        activity.setClub(club);
        activityRepository.save(activity);
        Long activityId = activity.getId();

        IntStream.range(0, images.size())
                .forEach(orderIndex -> {
                    Long fileId = fileService.uploadObjectAndSaveFile(images.get(orderIndex));
                    activityImageService.saveNewActivityImage(activityId, fileId, orderIndex);
                });

        return ActivityCommandResponse.of(activityId);
    }

    @Override
    public void updateActivity(Long activityId, UpdateActivityRequest updateActivityRequest,
                               List<MultipartFile> images) {

        Activity activity = getEntityById(activityId);
        activity.updateContentAndDate(updateActivityRequest.content(), updateActivityRequest.date());

        if (images != null) {
            List<Long> fileIds = activityImageService.getAllFileIds(activityId);

            // 매핑 삭제
            activityImageService.deleteAllByActivityId(activityId);

            // 파일 삭제
            fileIds.forEach(fileService::deleteObjectAndFile);

            // 새로 등록
            IntStream.range(0, images.size())
                    .forEach(orderIndex -> {
                        Long fileId = fileService.uploadObjectAndSaveFile(images.get(orderIndex));
                        activityImageService.saveNewActivityImage(activityId, fileId, orderIndex);
                    });
        }

    }

    @Override
    public void deleteActivity(Long activityId) {
        List<Long> fileIds = activityImageService.getAllFileIds(activityId);

        // 매핑 삭제
        activityImageService.deleteAllByActivityId(activityId);

        // 파일 삭제
        fileIds.forEach(fileService::deleteObjectAndFile);

        // 엔티티 삭제
        delete(activityId);
    }

    @Override
    public GetAllActivityResponse getAllActivity(Long clubId) {

        List<Activity> activityList = getAllByClubId(clubId);

        List<ActivityThumbnailDTO> activityThumbnailDTOList = activityList.stream()
                .map(activity -> {
                    Long activityId = activity.getId();
                    Long thumbnailId = activityImageService.getActivityThumbnailId(activity.getId());
                    FileDownloadDTO fileDownloadDTO = fileService.getFileDownloadDTO(thumbnailId);

                    return ActivityThumbnailDTO.of(activityId, fileDownloadDTO);
                }).toList();

        return GetAllActivityResponse.of(activityThumbnailDTOList);
    }

    @Override
    public GetSpecificActivityResponse getSpecificActivity(Long activityId) {
        Activity activity = activityRepository.findByIdWithClubAndImageFile(activityId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._ACTIVITY_NOT_FOUND));

        List<ActivityImageDTO> activityImageDTOList = activityImageRepository.findAllByActivityIdOrderByOrderIndexAsc(
                        activityId).stream()
                .map(activityImage
                        -> {
                    Long fileId = activityImage.getImageFile().getId();
                    String downloadUrl = s3Service.getDownloadUrl(fileId);
                    return ActivityImageDTO.of(activityImage.getOrderIndex(), downloadUrl);
                }).toList();

        return GetSpecificActivityResponse.of(
                activity, s3Service.getDownloadUrl(activity.getClub().getImageFile().getId()), activityImageDTOList);
    }

    @Override
    public RecentActivityLogResponse getRecentActivities() {
        List<Activity> activities = activityRepository.findRecentActivities(PageRequest.of(0, MAX_RECENT_ACTIVITY));

        List<RecentActivityLog> list = activities.stream()
                .map((activity) ->
                        of(s3Service.getDownloadUrl(
                                        activityImageRepository.findFirstByActivityIdOrderByIdAsc(activity.getId())
                                                .get().getImageFile().getId()), activity.getClub(),
                                s3Service.getDownloadUrl(activity.getClub().getImageFile().getId())))
                .toList();
        return RecentActivityLogResponse.of(list);
    }

    private Activity getEntityById(Long activityId) {
        return activityRepository.findById(activityId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._ACTIVITY_NOT_FOUND));
    }

    private void delete(Long activityId) {
        activityRepository.deleteById(activityId);
    }

    private List<Activity> getAllByClubId(Long clubId) {
        return activityRepository.findAllByClubId(clubId);
    }

}

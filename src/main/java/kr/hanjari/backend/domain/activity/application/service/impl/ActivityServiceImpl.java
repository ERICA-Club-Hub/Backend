package kr.hanjari.backend.domain.activity.application.service.impl;

import static kr.hanjari.backend.domain.activity.presentation.dto.response.RecentActivityLogResponse.RecentActivityLog.of;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;
import kr.hanjari.backend.domain.activity.domain.entity.Activity;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.file.domain.entity.File;
import kr.hanjari.backend.domain.activity.domain.entity.ActivityImageId;
import kr.hanjari.backend.domain.activity.domain.entity.ActivityImage;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.domain.activity.domain.repository.ActivityImageRepository;
import kr.hanjari.backend.domain.activity.domain.repository.ActivityRepository;
import kr.hanjari.backend.domain.club.domain.repository.ClubRepository;
import kr.hanjari.backend.domain.activity.application.service.ActivityService;
import kr.hanjari.backend.infrastructure.s3.S3Service;
import kr.hanjari.backend.domain.activity.presentation.dto.request.CreateActivityRequest;
import kr.hanjari.backend.domain.activity.presentation.dto.request.UpdateActivityRequest;
import kr.hanjari.backend.domain.activity.presentation.dto.response.ActivityImageDTO;
import kr.hanjari.backend.domain.activity.presentation.dto.response.ActivityThumbnailDTO;
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
    private final ClubRepository clubRepository;

    private final S3Service s3Service;

    @Override
    public Long createActivity(Long clubId, CreateActivityRequest createActivityRequest, List<MultipartFile> images) {

        Activity newActivity = createActivityRequest.toActivity();
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        newActivity.setClub(club);
        activityRepository.save(newActivity);

        IntStream.range(0, images.size())
                .forEach(i -> {
                    File newImage = s3Service.uploadFile(images.get(i));
                    ActivityImageId activityImageId = new ActivityImageId();
                    ActivityImage activityImage = ActivityImage.builder()
                            .id(activityImageId)
                            .activity(newActivity)
                            .imageFile(newImage)
                            .orderIndex(i)
                            .build();
                    activityImageRepository.save(activityImage);
                });

        return newActivity.getId();
    }

    @Override
    public void updateActivity(Long activityId, UpdateActivityRequest updateActivityRequest,
                               List<MultipartFile> images) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._ACTIVITY_NOT_FOUND));

        activity.updateContentAndDate(updateActivityRequest.content(), updateActivityRequest.date());

        if (images != null) {
            List<ActivityImage> activityImageList = activityImageRepository.findAllByActivityId(activityId);
            List<Long> fileIdToDeleteList = activityImageList.stream()
                    .map(activityImage -> {
                        Long fileId = activityImage.getImageFile().getId();
                        activityImageRepository.delete(activityImage);
                        return fileId;
                    }).toList();

            IntStream.range(0, images.size())
                    .forEach(i -> {
                        File newImage = s3Service.uploadFile(images.get(i));
                        ActivityImageId activityImageId = new ActivityImageId();
                        ActivityImage activityImage = ActivityImage.builder()
                                .id(activityImageId)
                                .activity(activity)
                                .imageFile(newImage)
                                .orderIndex(i)
                                .build();
                        activityImageRepository.save(activityImage);
                    });

            fileIdToDeleteList.forEach(s3Service::deleteFile);
        }
    }

    @Override
    public void deleteActivity(Long activityId) {
        if (!activityRepository.existsById(activityId)) {
            throw new GeneralException(ErrorStatus._ACTIVITY_NOT_FOUND);
        }

        List<ActivityImage> activityImages = activityImageRepository.findAllByActivityId(activityId);
        activityImages.forEach(
                activityImage -> {
                    Long fileIdForDelete = activityImage.getImageFile().getId();
                    activityImageRepository.delete(activityImage);
                    s3Service.deleteFile(fileIdForDelete);
                }
        );
        activityRepository.deleteById(activityId);
    }

    @Override
    public GetAllActivityResponse getAllActivity(Long clubId) {
        List<Activity> activityList = activityRepository.findAllByClubId(clubId);
        List<ActivityThumbnailDTO> activityThumbnailDTOList = activityList.stream()
                .map(activity -> {
                    Long activityId = activity.getId();
                    File thumbnail = activityImageRepository.findFirstByActivityIdOrderByIdAsc(activityId)
                            .get().getImageFile();
                    String thumbnailUrl = s3Service.getDownloadUrl(thumbnail.getId());
                    return ActivityThumbnailDTO.of(activityId, thumbnailUrl);
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
}

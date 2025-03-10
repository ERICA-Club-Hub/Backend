package kr.hanjari.backend.service.activity.impl;

import jakarta.transaction.Transactional;
import kr.hanjari.backend.domain.Activity;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.File;
import kr.hanjari.backend.domain.key.ActivityImageId;
import kr.hanjari.backend.domain.mapping.ActivityImage;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.ActivityImageRepository;
import kr.hanjari.backend.repository.ActivityRepository;
import kr.hanjari.backend.repository.ClubRepository;
import kr.hanjari.backend.security.token.JwtTokenProvider;
import kr.hanjari.backend.service.activity.ActivityService;
import kr.hanjari.backend.service.s3.S3Service;
import kr.hanjari.backend.web.dto.activity.request.CreateActivityRequest;
import kr.hanjari.backend.web.dto.activity.request.UpdateActivityRequest;
import kr.hanjari.backend.web.dto.activity.response.ActivityImageDTO;
import kr.hanjari.backend.web.dto.activity.response.ActivityThumbnailDTO;
import kr.hanjari.backend.web.dto.activity.response.GetAllActivityResponse;
import kr.hanjari.backend.web.dto.activity.response.GetSpecificActivityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

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
    public void updateActivity(Long activityId, UpdateActivityRequest updateActivityRequest, List<MultipartFile> images) {
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
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._ACTIVITY_NOT_FOUND));

        List<ActivityImageDTO> activityImageDTOList = activityImageRepository.findAllByActivityIdOrderByOrderIndexAsc(activityId).stream()
                .map(activityImage
                        -> {
                    Long fileId = activityImage.getImageFile().getId();
                    String downloadUrl = s3Service.getDownloadUrl(fileId);
                    return ActivityImageDTO.of(activityImage.getOrderIndex(), downloadUrl);
                }).toList();

        return GetSpecificActivityResponse.of(activity, activityImageDTOList);
    }
}

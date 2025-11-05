package kr.hanjari.backend.domain.activity.application.service;

import kr.hanjari.backend.domain.activity.domain.entity.Activity;
import kr.hanjari.backend.domain.activity.domain.entity.ActivityImage;
import kr.hanjari.backend.domain.activity.domain.entity.ActivityImageId;
import kr.hanjari.backend.domain.activity.domain.repository.ActivityImageRepository;
import kr.hanjari.backend.domain.activity.domain.repository.ActivityRepository;
import kr.hanjari.backend.domain.file.application.FileService;
import kr.hanjari.backend.domain.file.domain.entity.File;
import kr.hanjari.backend.domain.file.domain.repository.FileRepository;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityImageService {

    private final ActivityImageRepository activityImageRepository;
    private final ActivityRepository activityRepository;
    private final FileRepository fileRepository;

    private final FileService fileService;

    public void saveNewActivityImage(Long activityId, Long fileId, Integer orderIndex) {

        Activity activity = activityRepository.getReferenceById(activityId);
        File file = fileRepository.getReferenceById(fileId);

        ActivityImage activityImage = ActivityImage.builder()
                .id(new ActivityImageId(activityId, fileId))
                .activity(activity)
                .imageFile(file)
                .orderIndex(orderIndex)
                .build();

        activityImageRepository.save(activityImage);
    }

    public void deleteByFileId(Long fileId) {
        activityImageRepository.deleteByImageFileId(fileId);
    }

    public void deleteAllByActivityId(Long activityId) {
        activityImageRepository.deleteAllByActivityId(activityId);
    }

    public List<Long> getAllFileIds(Long activityId) {
        return activityImageRepository.findAllFileIdsByActivityId(activityId);
    }

    public Long getActivityThumbnailId(Long activityId) {
        return activityImageRepository.findFirstByActivityIdOrderByIdAsc(activityId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR))
                .getImageFile()
                .getId();
    }


}

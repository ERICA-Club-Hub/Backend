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
import kr.hanjari.backend.service.activity.ActivityService;
import kr.hanjari.backend.service.s3.S3Service;
import kr.hanjari.backend.web.dto.activity.request.CreateActivityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

        if (!images.isEmpty())
        {
            images.forEach(
                    image -> {
                        File newImage = s3Service.uploadFile(image);
                        ActivityImageId activityImageId = new ActivityImageId();
                        ActivityImage activityImage = ActivityImage.builder()
                                .id(activityImageId)
                                .activity(newActivity)
                                .imageFile(newImage)
                                .build();
                        activityImageRepository.save(activityImage);
                    }

            );
        }

        return newActivity.getId();
    }
}

package kr.hanjari.backend.domain.activity.application.service;

import java.util.List;
import kr.hanjari.backend.domain.activity.presentation.dto.request.CreateActivityRequest;
import kr.hanjari.backend.domain.activity.presentation.dto.request.UpdateActivityRequest;
import kr.hanjari.backend.domain.activity.presentation.dto.response.ActivityCommandResponse;
import kr.hanjari.backend.domain.activity.presentation.dto.response.GetAllActivityResponse;
import kr.hanjari.backend.domain.activity.presentation.dto.response.GetSpecificActivityResponse;
import kr.hanjari.backend.domain.activity.presentation.dto.response.RecentActivityLogResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ActivityService {
    ActivityCommandResponse createActivity(Long clubId, CreateActivityRequest createActivityRequest, List<MultipartFile> images);

    void updateActivity(Long activityId, UpdateActivityRequest updateActivityRequest, List<MultipartFile> images);

    void deleteActivity(Long activityId);

    GetAllActivityResponse getAllActivity(Long clubId);

    GetSpecificActivityResponse getSpecificActivity(Long activityId);

    RecentActivityLogResponse getRecentActivities();
}

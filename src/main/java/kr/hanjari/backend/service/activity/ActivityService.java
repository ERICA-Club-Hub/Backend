package kr.hanjari.backend.service.activity;

import kr.hanjari.backend.web.dto.activity.request.CreateActivityRequest;
import kr.hanjari.backend.web.dto.activity.request.UpdateActivityRequest;
import kr.hanjari.backend.web.dto.activity.response.GetAllActivityResponse;
import kr.hanjari.backend.web.dto.activity.response.GetSpecificActivityResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ActivityService {
    Long createActivity(Long clubId, CreateActivityRequest createActivityRequest, List<MultipartFile> images);
    void updateActivity(Long activityId, UpdateActivityRequest updateActivityRequest, List<MultipartFile> images);
    void deleteActivity(Long activityId);
    GetAllActivityResponse getAllActivity(Long clubId);
    GetSpecificActivityResponse getSpecificActivity(Long activityId);
}

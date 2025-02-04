package kr.hanjari.backend.service.activity;

import kr.hanjari.backend.domain.Activity;
import kr.hanjari.backend.web.dto.activity.request.CreateActivityRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ActivityService {
    Long createActivity(Long clubId, CreateActivityRequest createActivityRequest, List<MultipartFile> images);
}

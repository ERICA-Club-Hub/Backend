package kr.hanjari.backend.domain.activity.presentation.dto.response;

import kr.hanjari.backend.domain.activity.presentation.dto.ActivityThumbnailDTO;

import java.util.List;

public record GetAllActivityResponse(
        List<ActivityThumbnailDTO> activityThumbnailDTOList
) {
    public static GetAllActivityResponse of(List<ActivityThumbnailDTO> activityThumbnailDTOList) {
        return new GetAllActivityResponse(activityThumbnailDTOList);
    }
}

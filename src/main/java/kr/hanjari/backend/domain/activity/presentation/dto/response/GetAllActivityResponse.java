package kr.hanjari.backend.domain.activity.presentation.dto.response;

import java.util.List;

public record GetAllActivityResponse(
        List<ActivityThumbnailDTO> activityThumbnailDTOList
) {
    public static GetAllActivityResponse of(List<ActivityThumbnailDTO> activityThumbnailDTOList) {
        return new GetAllActivityResponse(activityThumbnailDTOList);
    }
}

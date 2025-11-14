package kr.hanjari.backend.domain.activity.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO for getting all activities response")
public record GetAllActivityResponse(
        @Schema(description = "List of activity thumbnail DTOs", nullable = false)
        List<ActivityThumbnailDTO> activityThumbnailDTOList
) {
    public static GetAllActivityResponse of(List<ActivityThumbnailDTO> activityThumbnailDTOList) {
        return new GetAllActivityResponse(activityThumbnailDTOList);
    }
}

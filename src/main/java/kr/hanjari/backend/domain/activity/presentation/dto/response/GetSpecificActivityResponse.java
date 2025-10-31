package kr.hanjari.backend.domain.activity.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.activity.domain.entity.Activity;
import kr.hanjari.backend.domain.activity.presentation.dto.ActivityImageDTO;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "DTO for getting a specific activity response")
public record GetSpecificActivityResponse(
        @Schema(description = "Club name", nullable = false, example = "Hanjari")
        String clubName,
        @Schema(description = "Club image URL", nullable = true, example = "https://.../club.png")
        String clubImageUrl,
        @Schema(description = "Activity content", nullable = false, example = "1st General Meeting of 2024")
        String content,
        @Schema(description = "Activity date", nullable = false, example = "2024-03-01")
        LocalDate date,
        @Schema(description = "List of activity image DTOs", nullable = false)
        List<ActivityImageDTO> activityImageDTOList
) {
    public static GetSpecificActivityResponse of(Activity activity, String clubImageUrl, List<ActivityImageDTO> activityImageUrlList) {
        return new GetSpecificActivityResponse(activity.getClub().getName(), clubImageUrl,
                activity.getContent(), activity.getDate(), activityImageUrlList);
    }
}

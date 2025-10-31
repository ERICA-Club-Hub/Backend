package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.draft.ScheduleDraft;

@Schema(description = "DTO for club schedule draft response")
public record ClubScheduleDraftResponse(
        @Schema(description = "List of club schedule drafts", nullable = false)
        List<ScheduleDraftResponse> schedules,
        @Schema(description = "Total number of elements", nullable = false, example = "10")
        Integer totalElements
) {
    public static ClubScheduleDraftResponse of(List<ScheduleDraft> schecduleList) {
        return new ClubScheduleDraftResponse(
                schecduleList.stream().map(ScheduleDraftResponse::of).toList(),
                schecduleList.size()
        );
    }
}


package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.draft.ScheduleDraft;

@Schema(description = "DTO for schedule draft response")
public record ScheduleDraftResponse(
        @Schema(description = "Schedule ID", nullable = false, example = "1")
        Long id,
        @Schema(description = "Month", nullable = false, example = "3")
        Integer month,
        @Schema(description = "Schedule content", nullable = false, example = "General meeting")
        String content
) {
    public static ScheduleDraftResponse of(ScheduleDraft schedule) {
        return new ScheduleDraftResponse(schedule.getId(), schedule.getMonth(), schedule.getContent());
    }
}

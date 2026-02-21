package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;

@Schema(description = "DTO for schedule response")
public record ScheduleResponse(
        @Schema(description = "Schedule ID", nullable = false, example = "1")
        Long id,
        @Schema(description = "Month", nullable = false, example = "3")
        Integer month,
        @Schema(description = "Schedule content", nullable = false, example = "General meeting")
        String content
) {
    public static ScheduleResponse of(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getMonth(),
                schedule.getContent()
        );
    }
}

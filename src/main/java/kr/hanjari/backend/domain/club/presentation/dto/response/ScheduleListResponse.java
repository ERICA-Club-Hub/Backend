package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;

@Schema(description = "DTO for schedule list response")
public record ScheduleListResponse(
        @Schema(description = "List of schedules", nullable = false)
        List<ScheduleResponse> schedules
) {
    public static ScheduleListResponse of(List<Schedule> schedules) {
        return new ScheduleListResponse(
                schedules.stream().map(ScheduleResponse::of).toList()
        );
    }
}

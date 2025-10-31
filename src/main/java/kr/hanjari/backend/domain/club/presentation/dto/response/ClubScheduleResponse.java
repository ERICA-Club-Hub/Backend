package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;

@Schema(description = "DTO for club schedule response")
public record ClubScheduleResponse(
        @Schema(description = "List of club schedules", nullable = false)
        List<ScheduleResponse> schedules,
        @Schema(description = "Total number of elements", nullable = false, example = "10")
        Integer totalElements
) {
    public static ClubScheduleResponse of(List<Schedule> schedules) {
        return new ClubScheduleResponse(
                schedules.stream().map(ScheduleResponse::of).toList(),
                schedules.size()
        );
    }
}

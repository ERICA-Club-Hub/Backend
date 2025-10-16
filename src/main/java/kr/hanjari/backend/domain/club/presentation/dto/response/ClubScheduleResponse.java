package kr.hanjari.backend.domain.club.presentation.dto.response;

import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;

public record ClubScheduleResponse(
        List<ScheduleResponse> schedules,
        Integer totalElements
) {
    public static ClubScheduleResponse of(List<Schedule> schedules) {
        return new ClubScheduleResponse(
                schedules.stream().map(ScheduleResponse::of).toList(),
                schedules.size()
        );
    }
}

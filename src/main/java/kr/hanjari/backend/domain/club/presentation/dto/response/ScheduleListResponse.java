package kr.hanjari.backend.domain.club.presentation.dto.response;

import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;

public record ScheduleListResponse(List<ScheduleResponse> schedules) {
    public static ScheduleListResponse of(List<Schedule> schedules) {
        return new ScheduleListResponse(
                schedules.stream().map(ScheduleResponse::of).toList()
        );
    }
}

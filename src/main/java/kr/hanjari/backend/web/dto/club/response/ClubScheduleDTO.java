package kr.hanjari.backend.web.dto.club.response;

import java.util.List;
import kr.hanjari.backend.domain.Schedule;

public record ClubScheduleDTO (
        List<ScheduleDTO> activities,
        Integer totalElements
) {
    public static ClubScheduleDTO of(List<Schedule> schedules) {
        return new ClubScheduleDTO(
                schedules.stream().map(ScheduleDTO::of).toList(),
                schedules.size()
        );
    }
}

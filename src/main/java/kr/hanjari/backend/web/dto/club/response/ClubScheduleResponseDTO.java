package kr.hanjari.backend.web.dto.club.response;

import java.util.List;
import kr.hanjari.backend.domain.Schedule;

public record ClubScheduleResponseDTO(
        List<ScheduleResponseDTO> schedules,
        Integer totalElements
) {
    public static ClubScheduleResponseDTO of(List<Schedule> schedules) {
        return new ClubScheduleResponseDTO(
                schedules.stream().map(ScheduleResponseDTO::of).toList(),
                schedules.size()
        );
    }
}

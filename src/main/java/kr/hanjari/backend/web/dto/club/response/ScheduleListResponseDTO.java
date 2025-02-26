package kr.hanjari.backend.web.dto.club.response;

import java.util.List;
import kr.hanjari.backend.domain.Schedule;

public record ScheduleListResponseDTO(List<ScheduleResponseDTO> schedules) {
    public static ScheduleListResponseDTO of(List<Schedule> schedules) {
        return new ScheduleListResponseDTO(
                schedules.stream().map(ScheduleResponseDTO::of).toList()
        );
    }
}

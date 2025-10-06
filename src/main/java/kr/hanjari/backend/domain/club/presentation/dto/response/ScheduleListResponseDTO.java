package kr.hanjari.backend.domain.club.presentation.dto.response;

import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;

public record ScheduleListResponseDTO(List<ScheduleResponseDTO> schedules) {
    public static ScheduleListResponseDTO of(List<Schedule> schedules) {
        return new ScheduleListResponseDTO(
                schedules.stream().map(ScheduleResponseDTO::of).toList()
        );
    }
}

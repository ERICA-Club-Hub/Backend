package kr.hanjari.backend.domain.club.presentation.dto.response;

import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;

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

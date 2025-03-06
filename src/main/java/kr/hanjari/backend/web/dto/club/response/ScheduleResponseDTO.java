package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.Schedule;

public record ScheduleResponseDTO(Long id, Integer month, String content) {
    public static ScheduleResponseDTO of(Schedule schedule) {
        return new ScheduleResponseDTO(
                schedule.getId(),
                schedule.getMonth(),
                schedule.getContent()
        );
    }
}


package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.Schedule;

public record ScheduleDTO(Long id, Integer month, String content) {
    public static ScheduleDTO of(Schedule schedule) {
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getMonth(),
                schedule.getContent()
        );
    }
}


package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;

public record ScheduleResponseDTO(Long id, Integer month, String content) {
    public static ScheduleResponseDTO of(Schedule schedule) {
        return new ScheduleResponseDTO(
                schedule.getId(),
                schedule.getMonth(),
                schedule.getContent()
        );
    }
}


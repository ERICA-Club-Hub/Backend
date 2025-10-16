package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;

public record ScheduleResponse(Long id, Integer month, String content) {
    public static ScheduleResponse of(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getMonth(),
                schedule.getContent()
        );
    }
}


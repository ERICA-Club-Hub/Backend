package kr.hanjari.backend.domain.activity.presentation.dto.request;

import kr.hanjari.backend.domain.activity.domain.entity.Activity;

import java.time.LocalDate;

public record CreateActivityRequest(
        String content,
        LocalDate date
) {
    public Activity toEntity() {
        return Activity.builder()
                .content(content)
                .date(date)
                .build();
    }
}

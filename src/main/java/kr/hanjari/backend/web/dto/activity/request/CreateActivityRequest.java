package kr.hanjari.backend.web.dto.activity.request;

import kr.hanjari.backend.domain.Activity;

import java.time.LocalDate;

public record CreateActivityRequest(
        String content,
        LocalDate date
) {
    public Activity toActivity() {
        return Activity.builder()
                .content(content)
                .date(date)
                .build();
    }
}

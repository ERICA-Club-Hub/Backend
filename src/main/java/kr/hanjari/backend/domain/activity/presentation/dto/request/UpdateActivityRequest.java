package kr.hanjari.backend.domain.activity.presentation.dto.request;

import java.time.LocalDate;

public record UpdateActivityRequest(
        String content,
        LocalDate date
) {
}

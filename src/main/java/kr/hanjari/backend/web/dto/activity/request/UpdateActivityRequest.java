package kr.hanjari.backend.web.dto.activity.request;

import java.time.LocalDate;
import java.util.List;

public record UpdateActivityRequest(
        String content,
        LocalDate date
) {
}

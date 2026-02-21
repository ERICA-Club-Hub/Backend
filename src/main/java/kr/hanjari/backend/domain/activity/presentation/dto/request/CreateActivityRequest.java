package kr.hanjari.backend.domain.activity.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.hanjari.backend.domain.activity.domain.entity.Activity;

import java.time.LocalDate;

@Schema(description = "DTO for creating an activity")
public record CreateActivityRequest(
        @NotBlank(message = "Content is required.")
        @Schema(description = "The content of the activity", nullable = false, example = "1st General Meeting of 2024", requiredMode = Schema.RequiredMode.REQUIRED)
        String content,
        @NotNull(message = "Date is required.")
        @Schema(description = "The date of the activity", nullable = false, example = "2024-03-01", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDate date
) {
    public Activity toEntity() {
        return Activity.builder()
                .content(content)
                .date(date)
                .build();
    }
}

package kr.hanjari.backend.domain.activity.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "DTO for updating an activity")
public record UpdateActivityRequest(
        @NotBlank(message = "Content is required.")
        @Schema(description = "The content of the activity", nullable = false, example = "1st General Meeting of 2024", requiredMode = Schema.RequiredMode.REQUIRED)
        String content,
        @NotNull(message = "Date is required.")
        @Schema(description = "The date of the activity", nullable = false, example = "2024-03-01", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDate date
) {
}

package kr.hanjari.backend.domain.club.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO for club schedule request")
public record ClubScheduleRequest(
        @NotNull(message = "Month is required.")
        @Min(value = 1, message = "Month must be 1 or greater.")
        @Max(value = 12, message = "Month must be 12 or less.")
        @Schema(description = "Month", nullable = false, example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer month,
        @NotBlank(message = "Content is required.")
        @Schema(description = "Schedule content", nullable = false, example = "General meeting", requiredMode = Schema.RequiredMode.REQUIRED)
        String content,
        @Schema(description = "Schedule ID (required for updates)", example = "1")
        Long scheduleId
) {
}

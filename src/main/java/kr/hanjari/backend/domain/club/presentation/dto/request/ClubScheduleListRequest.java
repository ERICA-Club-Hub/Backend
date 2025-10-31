package kr.hanjari.backend.domain.club.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "DTO for club schedule list request")
public record ClubScheduleListRequest(
        @NotEmpty(message = "Schedule list cannot be empty.")
        @Valid
        @Schema(description = "List of club schedules", requiredMode = Schema.RequiredMode.REQUIRED)
        List<ClubScheduleRequest> schedules
) {
}

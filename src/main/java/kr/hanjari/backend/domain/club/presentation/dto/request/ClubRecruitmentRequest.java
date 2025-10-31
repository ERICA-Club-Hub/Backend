package kr.hanjari.backend.domain.club.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO for club recruitment request")
public record ClubRecruitmentRequest(
        @NotBlank(message = "Due date is required.")
        @Schema(description = "Recruitment due date", nullable = false, example = "2024-03-31", requiredMode = Schema.RequiredMode.REQUIRED)
        String due,
        @NotBlank(message = "Notice is required.")
        @Schema(description = "Recruitment notice", nullable = false, example = "Interview after document submission", requiredMode = Schema.RequiredMode.REQUIRED)
        String notice,
        @Schema(description = "Etc", example = "For further questions...")
        String etc
) {
}

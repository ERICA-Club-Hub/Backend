package kr.hanjari.backend.domain.club.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO for club introduction request")
public record ClubIntroductionRequest(
        @NotBlank(message = "Introduction is required.")
        @Schema(description = "The introduction of the club", nullable = false, example = "Hanjari is...", requiredMode = Schema.RequiredMode.REQUIRED)
        String introduction,
        @NotBlank(message = "Activity is required.")
        @Schema(description = "The activities of the club", nullable = false, example = "Regular meeting every Monday", requiredMode = Schema.RequiredMode.REQUIRED)
        String activity,
        @NotBlank(message = "Recruitment information is required.")
        @Schema(description = "Recruitment information", nullable = false, example = "Always open", requiredMode = Schema.RequiredMode.REQUIRED)
        String recruitment
) {
}

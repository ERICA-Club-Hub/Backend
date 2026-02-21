package kr.hanjari.backend.domain.auth.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO for login request")
public record LoginRequest(
        @NotBlank(message = "Code is required.")
        @Schema(description = "OAuth authorization code", nullable = false, example = "authorization_code", requiredMode = Schema.RequiredMode.REQUIRED)
        String code
) {
}

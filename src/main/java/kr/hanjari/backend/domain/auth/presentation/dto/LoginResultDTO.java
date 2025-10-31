package kr.hanjari.backend.domain.auth.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.auth.presentation.dto.response.LoginResponse;

@Schema(description = "DTO for login result")
public record LoginResultDTO(
        @Schema(description = "JWT token", nullable = false, example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,
        LoginResponse loginResponse
) {
    public static LoginResultDTO of(String token, LoginResponse loginResponse) {
        return new LoginResultDTO(token, loginResponse);
    }
}

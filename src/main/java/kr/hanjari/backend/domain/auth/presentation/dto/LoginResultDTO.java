package kr.hanjari.backend.domain.auth.presentation.dto;

import kr.hanjari.backend.domain.auth.presentation.dto.response.LoginResponse;

public record LoginResultDTO(
        String token,
        LoginResponse loginResponse
) {
    public static LoginResultDTO of(String token, LoginResponse loginResponse) {
        return new LoginResultDTO(token, loginResponse);
    }
}

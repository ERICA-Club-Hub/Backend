package kr.hanjari.backend.web.dto.auth;

import kr.hanjari.backend.web.dto.auth.response.LoginResponseDTO;

public record LoginResultDTO(
        String token,
        Long clubId,
        String clubName
) {
    public static LoginResultDTO of(String token, Long clubId, String clubName) {
        return new LoginResultDTO(token, clubId, clubName);
    }
}

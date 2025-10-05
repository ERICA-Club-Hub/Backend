package kr.hanjari.backend.domain.auth.presentation.dto;

public record LoginResultDTO(
        String token,
        Long clubId,
        String clubName
) {
    public static LoginResultDTO of(String token, Long clubId, String clubName) {
        return new LoginResultDTO(token, clubId, clubName);
    }
}

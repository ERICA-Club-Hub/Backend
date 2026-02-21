package kr.hanjari.backend.domain.auth.presentation.dto.response;

public record LoginResponse(
        Long clubId,
        String clubName
) {
    public static LoginResponse of(Long clubId, String clubName) {
        return new LoginResponse(clubId, clubName);
    }
}

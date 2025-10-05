package kr.hanjari.backend.domain.auth.presentation.dto.response;

public record LoginResponseDTO(
        Long clubId,
        String clubName
) {
    public static LoginResponseDTO of(Long clubId, String clubName) {
        return new LoginResponseDTO(clubId, clubName);
    }
}

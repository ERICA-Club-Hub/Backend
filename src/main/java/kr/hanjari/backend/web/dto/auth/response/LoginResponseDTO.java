package kr.hanjari.backend.web.dto.auth.response;

public record LoginResponseDTO(
        Long clubId,
        String clubName
) {
    public static LoginResponseDTO of(Long clubId, String clubName) {
        return new LoginResponseDTO(clubId, clubName);
    }
}

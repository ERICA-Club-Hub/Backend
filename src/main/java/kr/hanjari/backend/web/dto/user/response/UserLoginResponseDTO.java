package kr.hanjari.backend.web.dto.user.response;

public record UserLoginResponseDTO(String clubName, Long clubId) {
    public static UserLoginResponseDTO of(String clubName, Long clubId) {
        return new UserLoginResponseDTO(clubName, clubId);
    }
}

package kr.hanjari.backend.web.dto.user.response;

public record UserLoginResponseDTO(String accessToken, String clubName) {
    public static UserLoginResponseDTO of(String accessToken, String clubName) {
        return new UserLoginResponseDTO(accessToken, clubName);
    }
}

package kr.hanjari.backend.web.dto.user.response;

public record UserLoginResponseDTO(String accessToken, String clubName, String code) {
    public static UserLoginResponseDTO of(String accessToken, String clubName, String code) {
        return new UserLoginResponseDTO(accessToken, clubName, code);
    }
}

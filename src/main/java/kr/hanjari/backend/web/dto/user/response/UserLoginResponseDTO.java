package kr.hanjari.backend.web.dto.user.response;

public record UserLoginResponseDTO(String clubName) {
    public static UserLoginResponseDTO of(String clubName) {
        return new UserLoginResponseDTO(clubName);
    }
}

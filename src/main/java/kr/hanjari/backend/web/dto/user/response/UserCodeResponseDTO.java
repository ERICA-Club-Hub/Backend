package kr.hanjari.backend.web.dto.user.response;

public record UserCodeResponseDTO(String code, String clubName) {
    public static UserCodeResponseDTO of(String code, String clubName) {
        return new UserCodeResponseDTO(code, clubName);
    }
}

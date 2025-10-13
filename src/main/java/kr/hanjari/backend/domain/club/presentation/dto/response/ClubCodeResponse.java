package kr.hanjari.backend.domain.club.presentation.dto.response;

public record ClubCodeResponse(
        String code
) {
    public static ClubCodeResponse of(String code) {
        return new ClubCodeResponse(code);
    }
}

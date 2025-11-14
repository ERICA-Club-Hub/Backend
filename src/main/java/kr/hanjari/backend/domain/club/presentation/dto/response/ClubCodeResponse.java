package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for club code response")
public record ClubCodeResponse(
        @Schema(description = "Club code", nullable = false, example = "hanjari")
        String code
) {
    public static ClubCodeResponse of(String code) {
        return new ClubCodeResponse(code);
    }
}

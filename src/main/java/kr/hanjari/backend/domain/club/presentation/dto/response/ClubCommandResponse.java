package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for club command response")
public record ClubCommandResponse(
        @Schema(description = "Club ID", nullable = false, example = "1")
        Long clubId
) {

    public static ClubCommandResponse of(Long clubId) {
        return new ClubCommandResponse(clubId);
    }
}

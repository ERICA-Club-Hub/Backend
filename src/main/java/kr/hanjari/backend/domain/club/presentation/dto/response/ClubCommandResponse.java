package kr.hanjari.backend.domain.club.presentation.dto.response;

public record ClubCommandResponse(
        Long clubId
) {

    public static ClubCommandResponse of(Long clubId) {
        return new ClubCommandResponse(clubId);
    }
}

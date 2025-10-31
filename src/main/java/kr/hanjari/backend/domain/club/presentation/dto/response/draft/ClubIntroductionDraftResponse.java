package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.draft.IntroductionDraft;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubResponse;

@Schema(description = "DTO for club introduction draft response")
public record ClubIntroductionDraftResponse(
        @Schema(description = "Club response DTO", nullable = false)
        ClubResponse club,
        @Schema(description = "Club introduction content", nullable = true, example = "Hanjari is...")
        String introduction,
        @Schema(description = "Club activity content", nullable = true, example = "Regular meeting every Monday")
        String activity,
        @Schema(description = "Recruitment information", nullable = true, example = "Always open")
        String recruitment
) {
    public static ClubIntroductionDraftResponse of(Club club, IntroductionDraft introductionDraft,
                                                   String profileImageUrl) {
        return new ClubIntroductionDraftResponse(
                ClubResponse.of(club, profileImageUrl),
                introductionDraft.getContent1(),
                introductionDraft.getContent2(),
                introductionDraft.getContent3()
        );
    }

}

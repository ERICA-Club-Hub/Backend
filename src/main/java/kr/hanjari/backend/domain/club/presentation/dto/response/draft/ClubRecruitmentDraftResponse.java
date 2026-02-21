package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.draft.RecruitmentDraft;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubResponse;

@Schema(description = "DTO for club recruitment draft response")
public record ClubRecruitmentDraftResponse(
        @Schema(description = "Club response DTO", nullable = false)
        ClubResponse club,
        @Schema(description = "Recruitment due date", nullable = true, example = "2024-03-31")
        String due,
        @Schema(description = "Recruitment target", nullable = true, example = "Undergraduate students")
        String target,
        @Schema(description = "Recruitment notice", nullable = true, example = "Interview after document submission")
        String notice,
        @Schema(description = "Other recruitment information", nullable = true, example = "For further questions...")
        String etc
) {
    public static ClubRecruitmentDraftResponse of(Club club, RecruitmentDraft recruitment, String profileImageUrl) {
        return new ClubRecruitmentDraftResponse(
                ClubResponse.of(club, profileImageUrl),
                recruitment.getDue(),
                recruitment.getTarget(),
                recruitment.getNotice(),
                recruitment.getEtc()
        );
    }

}

package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.presentation.dto.response.CategoryResponse;

@Schema(description = "DTO for club detail draft response")
public record ClubDetailDraftResponse(
        @Schema(description = "Club description (one-liner)", nullable = false, example = "The best central club at Hanyang University ERICA")
        String description,
        @Schema(description = "Leader's name", nullable = false, example = "Gildong Hong")
        String leaderName,
        @Schema(description = "Leader's phone number", nullable = false, example = "010-1234-5678")
        String leaderPhone,
        @Schema(description = "Contact email", nullable = false, example = "hanjari@hanjari.com")
        String contactEmail,
        @Schema(description = "Membership fee", nullable = false, example = "10000")
        String membershipFee,
        @Schema(description = "SNS URL", nullable = true, example = "https://www.instagram.com/hanjari_")
        String snsAccount,
        @Schema(description = "Application URL", nullable = true, example = "https://forms.gle/...")
        String applicationUrl
) {
    public static ClubDetailDraftResponse of(ClubDetailDraft clubDetailDraft, Club club) {
        return new ClubDetailDraftResponse(
                club.getOneLiner(),
                clubDetailDraft.getLeaderName(),
                clubDetailDraft.getLeaderPhone(),
                clubDetailDraft.getContactEmail(),
                clubDetailDraft.getMembershipFee(),
                clubDetailDraft.getSnsAccount(),
                clubDetailDraft.getApplicationUrl()
        );
    }

}

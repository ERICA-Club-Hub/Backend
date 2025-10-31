package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.presentation.dto.response.CategoryResponse;

@Schema(description = "DTO for club detail draft response")
public record ClubDetailDraftResponse(
        @Schema(description = "Club name", nullable = false, example = "Hanjari")
        String name,
        @Schema(description = "Club description (one-liner)", nullable = false, example = "The best central club at Hanyang University ERICA")
        String description,
        @Schema(description = "Recruitment status", nullable = false, example = "RECRUITING")
        RecruitmentStatus recruitmentStatus,
        @Schema(description = "Leader's name", nullable = false, example = "Gildong Hong")
        String leaderName,
        @Schema(description = "Leader's phone number", nullable = false, example = "010-1234-5678")
        String leaderPhone,
        @Schema(description = "Club activities", nullable = false, example = "Regular meeting every Monday")
        String activities,
        @Schema(description = "Membership fee", nullable = false, example = "10000")
        Integer membershipFee,
        @Schema(description = "SNS URL", nullable = true, example = "https://www.instagram.com/hanjari_")
        String snsUrl,
        @Schema(description = "Application URL", nullable = true, example = "https://forms.gle/...")
        String applicationUrl,
        @Schema(description = "Club profile image URL", nullable = true, example = "https://.../profile.png")
        String profileImageUrl,
        @Schema(description = "Club category information", nullable = false)
        CategoryResponse category
) {
    public static ClubDetailDraftResponse of(ClubDetailDraft clubDetailDraft, Club club, String profileImage) {
        return new ClubDetailDraftResponse(
                club.getName(),
                club.getOneLiner(),
                clubDetailDraft.getRecruitmentStatus(),
                clubDetailDraft.getLeaderName(),
                clubDetailDraft.getLeaderPhone(),
                clubDetailDraft.getActivities(),
                clubDetailDraft.getMembershipFee(),
                clubDetailDraft.getSnsUrl(),
                clubDetailDraft.getApplicationUrl(),
                profileImage,
                CategoryResponse.from(club.getCategoryInfo())
        );
    }

}

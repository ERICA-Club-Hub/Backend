package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.club.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.presentation.dto.response.CategoryResponse;

public record ClubDetailDraftResponse(
        String name,
        String description,
        RecruitmentStatus recruitmentStatus,
        String leaderName,
        String leaderPhone,
        String activities,
        Integer membershipFee,
        String snsUrl,
        String applicationUrl,
        String profileImageUrl,
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

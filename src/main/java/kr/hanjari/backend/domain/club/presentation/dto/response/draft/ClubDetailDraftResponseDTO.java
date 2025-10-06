package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.club.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.presentation.dto.response.CategoryResponseDTO;

public record ClubDetailDraftResponseDTO(
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
        CategoryResponseDTO category
) {
    public static ClubDetailDraftResponseDTO of(ClubDetailDraft clubDetailDraft, Club club, String profileImage) {
        return new ClubDetailDraftResponseDTO(
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
                CategoryResponseDTO.from(club.getCategoryInfo())
        );
    }

}

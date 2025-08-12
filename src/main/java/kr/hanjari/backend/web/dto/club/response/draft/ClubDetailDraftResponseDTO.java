package kr.hanjari.backend.web.dto.club.response.draft;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.web.dto.club.response.CategoryResponseDTO;

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

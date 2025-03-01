package kr.hanjari.backend.web.dto.club.response.draft;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.enums.ClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;

public record ClubDetailDraftResponseDTO(
        String name,
        String description,
        ClubCategory category,
        RecruitmentStatus recruitmentStatus,
        String leaderName,
        String leaderPhone,
        String activities,
        Integer membershipFee,
        String snsUrl,
        String applicationUrl,
        String profileImageUrl
)  {
    public static ClubDetailDraftResponseDTO of(ClubDetailDraft clubDetailDraft, Club club, String profileImage) {
        return new ClubDetailDraftResponseDTO(
                club.getName(),
                club.getBriefIntroduction(),
                club.getCategory(),
                clubDetailDraft.getRecruitmentStatus(),
                clubDetailDraft.getLeaderName(),
                clubDetailDraft.getLeaderPhone(),
                clubDetailDraft.getActivities(),
                clubDetailDraft.getMembershipFee(),
                clubDetailDraft.getSnsUrl(),
                clubDetailDraft.getApplicationUrl(),
                profileImage
        );
    }
}

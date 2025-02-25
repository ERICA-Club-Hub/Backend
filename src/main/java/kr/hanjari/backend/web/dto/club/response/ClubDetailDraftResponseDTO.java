package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;

public record ClubDetailDraftResponseDTO(
        RecruitmentStatus recruitmentStatus,
        String leaderName,
        String leaderPhone,
        String activities,
        Integer membershipFee,
        String snsUrl,
        String applicationUrl
)  {
    public static ClubDetailDraftResponseDTO of(ClubDetailDraft clubDetailDraft) {
        return new ClubDetailDraftResponseDTO(
                clubDetailDraft.getRecruitmentStatus(),
                clubDetailDraft.getLeaderName(),
                clubDetailDraft.getLeaderPhone(),
                clubDetailDraft.getActivities(),
                clubDetailDraft.getMembershipFee(),
                clubDetailDraft.getSnsUrl(),
                clubDetailDraft.getApplicationUrl()
        );
    }
}

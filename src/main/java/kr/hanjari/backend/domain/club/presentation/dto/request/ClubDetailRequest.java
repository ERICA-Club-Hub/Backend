package kr.hanjari.backend.domain.club.presentation.dto.request;

import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;

public record ClubDetailRequest(
        RecruitmentStatus recruitmentStatus,
        String leaderName,
        String leaderPhone,
        String activities,
        Integer membershipFee,
        String snsUrl,
        String applicationUrl
) {
}

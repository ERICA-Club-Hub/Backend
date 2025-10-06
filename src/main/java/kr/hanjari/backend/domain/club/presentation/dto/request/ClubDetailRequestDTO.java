package kr.hanjari.backend.domain.club.presentation.dto.request;

import kr.hanjari.backend.domain.club.enums.RecruitmentStatus;

public record ClubDetailRequestDTO(
        RecruitmentStatus recruitmentStatus,
        String leaderName,
        String leaderPhone,
        String activities,
        Integer membershipFee,
        String snsUrl,
        String applicationUrl
) {
}

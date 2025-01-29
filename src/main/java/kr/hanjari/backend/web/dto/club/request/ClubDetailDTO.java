package kr.hanjari.backend.web.dto.club.request;

import kr.hanjari.backend.domain.enums.RecruitmentStatus;

public record ClubDetailDTO(
        RecruitmentStatus recruitmentStatus,
        String leaderName,
        String leaderEmail,
        String leaderPhone,
        String activities,
        String snsUrl,
        String applicationUrl
) {
}

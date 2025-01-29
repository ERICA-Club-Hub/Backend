package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.enums.ClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;

public record ClubDTO(
        Long id,
        String name,
        String description,
        ClubCategory category,
        RecruitmentStatus recruitmentStatus,
        String profileImageUrl,
        String activities,
        String leaderName,
        String leaderEmail,
        String leaderPhone,
        Integer membershipFee,
        String snsUrl,
        String applicationUrl
) {
    public static ClubDTO of(Club club) {
        return new ClubDTO(
                club.getId(),
                club.getName(),
                club.getBriefIntroduction(),
                club.getCategory(),
                club.getRecruitmentStatus(),
                club.getImageFile().getFileKey(),
                club.getMeetingSchedule(),
                club.getLeaderName(),
                club.getLeaderEmail(),
                club.getLeaderPhone(),
                club.getMembershipFee(),
                club.getSnsUrl(),
                club.getApplicationUrl()
        );
    }
}

package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.enums.ClubType;
import kr.hanjari.backend.domain.club.enums.RecruitmentStatus;

public record ClubResponseDTO(
        Long id,
        String name,
        String description,
        RecruitmentStatus recruitmentStatus,
        String profileImageUrl,
        String activities,
        String leaderName,
        String leaderEmail,
        String leaderPhone,
        Integer membershipFee,
        String snsUrl,
        String applicationUrl,
        ClubType clubType
//        CategoryResponseDTO category
) {
    public static ClubResponseDTO of(Club club, String profileImageUrl) {
        return new ClubResponseDTO(
                club.getId(),
                club.getName(),
                club.getOneLiner(),
                club.getRecruitmentStatus(),
                profileImageUrl,
                club.getMeetingSchedule(),
                club.getLeaderName(),
                club.getLeaderEmail(),
                club.getLeaderPhone(),
                club.getMembershipFee(),
                club.getSnsUrl(),
                club.getApplicationUrl(),
                club.getCategoryInfo().getClubType()
//                CategoryResponseDTO.from(club.getCategoryInfo())
        );
    }
}

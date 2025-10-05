package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.enums.RecruitmentStatus;

public record ClubOverviewResponseDTO(
        Long id,
        String name,
        String description,
        RecruitmentStatus recruitmentStatus,
        String profileImageUrl,
        String applicationUrl,
        CategoryResponseDTO category
) {
    public static ClubOverviewResponseDTO of(Club club, String profileImageUrl) {
        return new ClubOverviewResponseDTO(
                club.getId(),
                club.getName(),
                club.getOneLiner(),
                club.getRecruitmentStatus(),
                profileImageUrl,
                club.getApplicationUrl(),
                CategoryResponseDTO.from(club.getCategoryInfo())
        );
    }
}

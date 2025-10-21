package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;

public record ClubOverviewResponse(
        Long id,
        String name,
        String description,
        RecruitmentStatus recruitmentStatus,
        String profileImageUrl,
        String applicationUrl,
        CategoryResponse category
) {
    public static ClubOverviewResponse of(Club club, String profileImageUrl) {
        return new ClubOverviewResponse(
                club.getId(),
                club.getName(),
                club.getOneLiner(),
                club.getRecruitmentStatus(),
                profileImageUrl,
                club.getApplicationUrl(),
                CategoryResponse.from(club.getCategoryInfo())
        );
    }
}

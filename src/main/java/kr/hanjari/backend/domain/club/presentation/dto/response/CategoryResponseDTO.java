package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.domain.entity.ClubCategoryInfo;

public record CategoryResponseDTO(
        String clubCategoryName,
        String centralCategoryName,
        String unionCategoryName,
        String collegeName,
        String departmentName
) {
    public static CategoryResponseDTO from(ClubCategoryInfo categoryInfo) {
        return new CategoryResponseDTO(
                categoryInfo.getClubType().getDescription(),
                categoryInfo.getCentralCategory() == null ? null : categoryInfo.getCentralCategory().getDescription(),
                categoryInfo.getUnionCategory() == null ? null : categoryInfo.getUnionCategory().getDescription(),
                categoryInfo.getCollege() == null ? null : categoryInfo.getCollege().getDescription(),
                categoryInfo.getDepartment() == null ? null : categoryInfo.getDepartment().getDescription()
        );
    }
}
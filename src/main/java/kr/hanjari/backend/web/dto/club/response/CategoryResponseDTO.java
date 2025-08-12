package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.vo.ClubCategoryInfo;

public record CategoryResponseDTO(
        String centralCategoryName,
        String unionCategoryName,
        String collegeName,
        String departmentName
) {
    public static CategoryResponseDTO from(ClubCategoryInfo categoryInfo) {
        return new CategoryResponseDTO(
                categoryInfo.getCentralCategory().getDescription(),
                categoryInfo.getUnionCategory().getDescription(),
                categoryInfo.getCollege().getDescription(),
                categoryInfo.getDepartment().getDescription()
        );
    }
}
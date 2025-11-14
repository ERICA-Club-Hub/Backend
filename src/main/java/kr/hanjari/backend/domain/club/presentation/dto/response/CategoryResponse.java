package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.ClubCategoryInfo;

@Schema(description = "DTO for category response")
public record CategoryResponse(
        @Schema(description = "Club category name", nullable = false, example = "Central Club")
        String clubCategoryName,
        @Schema(description = "Central category name", nullable = true, example = "Academic")
        String centralCategoryName,
        @Schema(description = "Union category name", nullable = true, example = "IT")
        String unionCategoryName,
        @Schema(description = "College name", nullable = true, example = "College of Software Convergence")
        String collegeName,
        @Schema(description = "Department name", nullable = true, example = "Department of Computer Science and Engineering")
        String departmentName
) {
    public static CategoryResponse from(ClubCategoryInfo categoryInfo) {
        return new CategoryResponse(
                categoryInfo.getClubType().getDescription(),
                categoryInfo.getCentralCategory() == null ? null : categoryInfo.getCentralCategory().getDescription(),
                categoryInfo.getUnionCategory() == null ? null : categoryInfo.getUnionCategory().getDescription(),
                categoryInfo.getCollege() == null ? null : categoryInfo.getCollege().getDescription(),
                categoryInfo.getDepartment() == null ? null : categoryInfo.getDepartment().getDescription()
        );
    }
}
package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.ClubCategoryInfo;
import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.domain.enums.ClubType;
import kr.hanjari.backend.domain.club.domain.enums.College;
import kr.hanjari.backend.domain.club.domain.enums.Department;
import kr.hanjari.backend.domain.club.domain.enums.UnionClubCategory;

@Schema(description = "DTO for category response")
public record CategoryResponse(
        @Schema(description = "Club category name", nullable = false, example = "Central Club")
        ClubType clubCategoryName,
        @Schema(description = "Central category name", nullable = true, example = "Academic")
        CentralClubCategory centralCategoryName,
        @Schema(description = "Union category name", nullable = true, example = "IT")
        UnionClubCategory unionCategoryName,
        @Schema(description = "College name", nullable = true, example = "College of Software Convergence")
        College collegeName,
        @Schema(description = "Department name", nullable = true, example = "Department of Computer Science and Engineering")
        Department departmentName
) {
    public static CategoryResponse from(ClubCategoryInfo categoryInfo) {
        return new CategoryResponse(
                categoryInfo.getClubType(),
                categoryInfo.getCentralCategory() == null ? null : categoryInfo.getCentralCategory(),
                categoryInfo.getUnionCategory() == null ? null : categoryInfo.getUnionCategory(),
                categoryInfo.getCollege() == null ? null : categoryInfo.getCollege(),
                categoryInfo.getDepartment() == null ? null : categoryInfo.getDepartment()
        );
    }
    public static String getTag(ClubCategoryInfo categoryInfo) {
      if (categoryInfo.getClubType() == ClubType.CENTRAL) {
          return categoryInfo.getCentralCategory().toString();
      } else if (categoryInfo.getClubType() == ClubType.UNION) {
          return categoryInfo.getUnionCategory().toString();
      } else if (categoryInfo.getClubType() == ClubType.DEPARTMENT) {
          return categoryInfo.getDepartment().toString();
      } else if (categoryInfo.getClubType() == ClubType.COLLEGE) {
          return categoryInfo.getCollege().toString();
      } else {
          return "Unknown";
      }
    }
}
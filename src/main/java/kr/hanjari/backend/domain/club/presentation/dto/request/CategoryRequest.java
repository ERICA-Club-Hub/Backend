package kr.hanjari.backend.domain.club.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.domain.enums.College;
import kr.hanjari.backend.domain.club.domain.enums.Department;
import kr.hanjari.backend.domain.club.domain.enums.UnionClubCategory;

@Schema(description = "Club Category")
public record CategoryRequest(
    @Schema(description = "Central club category", example = "ACADEMIC")
    CentralClubCategory central,
    @Schema(description = "Union club category", example = "IT")
    UnionClubCategory union,
    @Schema(description = "College", example = "SOFTWARE_CONVERGENCE")
    College college,
    @Schema(description = "Department", example = "COMPUTER_SCIENCE_ENGINEERING")
    Department department
) {

}

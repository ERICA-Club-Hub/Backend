package kr.hanjari.backend.domain.club.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.domain.enums.ClubType;
import kr.hanjari.backend.domain.club.domain.enums.College;
import kr.hanjari.backend.domain.club.domain.enums.Department;
import kr.hanjari.backend.domain.club.domain.enums.UnionClubCategory;
import kr.hanjari.backend.domain.common.command.CategoryCommand;

@Schema(description = "DTO for basic club information request")
public record ClubBasicInformationUpdateRequest(
        @NotBlank(message = "Club name is required.")
        @Schema(description = "Club name", nullable = false, example = "Hanjari", requiredMode = Schema.RequiredMode.REQUIRED)
        String clubName,
        @NotNull(message = "Club type is required.")
        @Schema(description = "Club type", nullable = false, example = "CENTRAL", requiredMode = Schema.RequiredMode.REQUIRED)
        ClubType clubType,
        @NotNull(message = "Category is required.")
        @Schema(description = "Category information", requiredMode = Schema.RequiredMode.REQUIRED)
        Category category,
        @NotBlank(message = "One-liner is required.")
        @Schema(description = "A short introduction of the club", nullable = false, example = "The best central club at Hanyang University ERICA", requiredMode = Schema.RequiredMode.REQUIRED)
        String oneLiner
) {

    @Schema(description = "Club Category")
    public record Category(
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

    public void validate() {
        if (clubType == null) {
            throw new IllegalArgumentException("clubType is required.");
        }
        if (category == null) {
            throw new IllegalArgumentException("category is required.");
        }

        switch (clubType) {
            case CENTRAL -> { // Central Club
                requireNonNull(category.central(), "For CENTRAL type, centralCategory is required.");
                requireNull(category.union(), "For CENTRAL type, unionCategory cannot be specified.");
                requireNull(category.college(), "For CENTRAL type, college cannot be specified.");
                requireNull(category.department(), "For CENTRAL type, department cannot be specified.");
            }
            case UNION -> { // Union Club
                requireNonNull(category.union(), "For UNION type, unionCategory is required.");
                requireNull(category.central(), "For UNION type, centralCategory cannot be specified.");
                requireNull(category.college(), "For UNION type, college cannot be specified.");
                requireNull(category.department(), "For UNION type, department cannot be specified.");
            }
            case COLLEGE -> { // College Club (no department info)
                requireNonNull(category.college(), "For COLLEGE type, college is required.");
                requireNull(category.central(), "For COLLEGE type, centralCategory cannot be specified.");
                requireNull(category.union(), "For COLLEGE type, unionCategory cannot be specified.");
                requireNull(category.department(), "For COLLEGE type, department cannot be specified.");
            }
            case DEPARTMENT -> { // Department Club (with department info)
                requireNull(category.union(), "For DEPARTMENT type, unionCategory cannot be specified.");
                requireNull(category.central(), "For DEPARTMENT type, centralCategory cannot be specified.");
                requireNonNull(category.college(), "For DEPARTMENT type, college is required.");
                requireNonNull(category.department(), "For DEPARTMENT type, department is required.");
            }
        }
    }

    private static void requireNonNull(Object o, String m) {
        if (o == null) {
            throw new IllegalArgumentException(m);
        }
    }

    private static void requireNull(Object o, String m) {
        if (o != null) {
            throw new IllegalArgumentException(m);
        }
    }

    public CategoryCommand toCategoryCommand() {
        return new CategoryCommand(
                clubType,
                category.central(),
                category.union(),
                category.college(),
                category.department()
        );
    }

}


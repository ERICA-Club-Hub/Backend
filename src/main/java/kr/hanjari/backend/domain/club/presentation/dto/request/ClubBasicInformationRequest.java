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
public record ClubBasicInformationRequest(
        @NotBlank(message = "Club name is required.")
        @Schema(description = "Club name", nullable = false, example = "Hanjari", requiredMode = Schema.RequiredMode.REQUIRED)
        String clubName,
        @NotBlank(message = "Leader's email is required.")
        @Email(message = "Invalid email format.")
        @Schema(description = "Leader's email", nullable = false, example = "leader@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        String leaderEmail,
        @NotNull(message = "Club type is required.")
        @Schema(description = "Club type", nullable = false, example = "CENTRAL", requiredMode = Schema.RequiredMode.REQUIRED)
        ClubType clubType,
        @NotNull(message = "Category is required.")
        @Schema(description = "Category information", requiredMode = Schema.RequiredMode.REQUIRED)
        CategoryRequest category,
        @NotBlank(message = "One-liner is required.")
        @Schema(description = "A short introduction of the club", nullable = false, example = "The best central club at Hanyang University ERICA", requiredMode = Schema.RequiredMode.REQUIRED)
        String oneLiner,
        @NotBlank(message = "Brief introduction is required.")
        @Schema(description = "A brief introduction of the club", nullable = false, example = "Hanjari is a central club at Hanyang University ERICA.", requiredMode = Schema.RequiredMode.REQUIRED)
        String briefIntroduction
) {

}


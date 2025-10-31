package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for club registration response")
public record ClubRegistrationResponse(
        @Schema(description = "Club registration ID", nullable = false, example = "1")
        Long clubRegistrationId,
        @Schema(description = "Club name", nullable = false, example = "Hanjari")
        String clubName,
        @Schema(description = "Leader's email", nullable = false, example = "leader@example.com")
        String leaderEmail,
        @Schema(description = "One-liner introduction", nullable = false, example = "The best central club at Hanyang University ERICA")
        String oneLiner,
        @Schema(description = "Brief introduction", nullable = false, example = "Hanjari is a central club at Hanyang University ERICA.")
        String briefIntroduction,
        @Schema(description = "Club category information", nullable = false)
        CategoryResponse category
) {
    public static ClubRegistrationResponse from(
            kr.hanjari.backend.domain.club.domain.entity.ClubRegistration clubRegistration) {
        return new ClubRegistrationResponse(
                clubRegistration.getId(),
                clubRegistration.getName(),
                clubRegistration.getLeaderEmail(),
                clubRegistration.getOneLiner(),
                clubRegistration.getBriefIntroduction(),
                CategoryResponse.from(clubRegistration.getCategoryInfo())
        );
    }
}

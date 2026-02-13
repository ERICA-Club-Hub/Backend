package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.ClubCategoryInfo;

@Schema(description = "DTO for club registration response")
public record ClubRegistrationResponse(
        @Schema(description = "Club registration ID", nullable = false, example = "1")
        Long clubRegistrationId,
        @Schema(description = "Club name", nullable = false, example = "Hanjari")
        String clubName,
//        @Schema(description = "Leader's email", nullable = false, example = "leader@example.com")
//        String leaderEmail,
//        @Schema(description = "One-liner introduction", nullable = false, example = "The best central club at Hanyang University ERICA")
//        String oneLiner,
        @Schema(description = "Brief introduction", nullable = false, example = "Hanjari is a central club at Hanyang University ERICA.")
        String briefIntroduction,
        @Schema(description = "Club category information", nullable = false)
        CategoryResponse category,
        String profileImageUrl
) {
//    public static ClubRegistrationResponse from(
//            kr.hanjari.backend.domain.club.domain.entity.ClubRegistration clubRegistration) {
//        return new ClubRegistrationResponse(
//                clubRegistration.getId(),
//                clubRegistration.getName(),
//                clubRegistration.getLeaderEmail(),
////                clubRegistration.getOneLiner(),
////                clubRegistration.getBriefIntroduction(),
//                CategoryResponse.from(clubRegistration.getCategoryInfo()),
//
//        );
//    }

    public static ClubRegistrationResponse of(
            Long clubRegistrationId,
            String clubName,
            String briefIntroduction,
            ClubCategoryInfo categoryInfo,
            String profileImageUrl
    ) {
        return new ClubRegistrationResponse(
                clubRegistrationId,
                clubName,
                briefIntroduction,
                CategoryResponse.from(categoryInfo),
                profileImageUrl
        );
    }
}

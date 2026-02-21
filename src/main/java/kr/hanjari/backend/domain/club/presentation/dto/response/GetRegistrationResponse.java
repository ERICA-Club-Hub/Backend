package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.domain.entity.ClubCategoryInfo;

public record GetRegistrationResponse(
        Long clubRegistrationId,
        String clubName,
        String leaderEmail,
        CategoryResponse category,
        String oneLiner,
        String briefIntroduction,
        String profileImageUrl
) {
    public static GetRegistrationResponse of(
            Long clubRegistrationId,
            String clubName,
            String leaderEmail,
            ClubCategoryInfo categoryInfo,
            String oneLiner,
            String briefIntroduction,
            String profileImageUrl
    ) {
        return new GetRegistrationResponse(
                clubRegistrationId,
                clubName,
                leaderEmail,
                CategoryResponse.from(categoryInfo),
                oneLiner,
                briefIntroduction,
                profileImageUrl
        );
    }
}

package kr.hanjari.backend.domain.club.presentation.dto.response;

public record ClubRegistrationResponse(
        Long clubRegistrationId,
        String clubName,
        String leaderEmail,
        String oneLiner,
        String briefIntroduction,
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

package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.ClubRegistration;


public record ClubRegistrationDTO(
        Long clubRegistrationId,
        String clubName,
        String category,
        String leaderEmail,
        String oneLiner,
        String briefIntroduction
) {
    public static ClubRegistrationDTO from(ClubRegistration clubRegistration) {
        return new ClubRegistrationDTO(
                clubRegistration.getId(),
                clubRegistration.getName(),
                clubRegistration.getCategory().name(),
                clubRegistration.getLeaderEmail(),
                clubRegistration.getOneLiner(),
                clubRegistration.getBriefIntroduction()
        );
    }
}

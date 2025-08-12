package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.ClubRegistration;


public record ClubRegistrationDTO(
        Long clubRegistrationId,
        String clubName,
        String leaderEmail,
        String oneLiner,
        String briefIntroduction,
        CategoryResponseDTO category
) {
    public static ClubRegistrationDTO from(ClubRegistration clubRegistration) {
        return new ClubRegistrationDTO(
                clubRegistration.getId(),
                clubRegistration.getName(),
                clubRegistration.getLeaderEmail(),
                clubRegistration.getOneLiner(),
                clubRegistration.getBriefIntroduction(),
                CategoryResponseDTO.from(clubRegistration.getCategoryInfo())
        );
    }
}

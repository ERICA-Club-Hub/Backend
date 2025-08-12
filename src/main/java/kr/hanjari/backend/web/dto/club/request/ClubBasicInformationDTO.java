package kr.hanjari.backend.web.dto.club.request;

import kr.hanjari.backend.domain.ClubRegistration;
import kr.hanjari.backend.domain.enums.CentralClubCategory;


public record ClubBasicInformationDTO(String clubName,
                                      String leaderEmail,
                                      String category,
                                      String oneLiner,
                                      String briefIntroduction
) {

    public static ClubBasicInformationDTO of(String clubName, String leaderEmail, String category, String oneLiner,
                                             String briefIntroduction) {
        return new ClubBasicInformationDTO(clubName, leaderEmail, category, oneLiner, briefIntroduction);
    }

    public ClubRegistration toClubRegistration() {
        return ClubRegistration.builder()
                .name(clubName())
                .category(CentralClubCategory.valueOf(category()))
                .leaderEmail(leaderEmail())
                .oneLiner(oneLiner())
                .briefIntroduction(briefIntroduction())
                .build();
    }
}

package kr.hanjari.backend.web.dto.club.request;

import kr.hanjari.backend.domain.ClubRegistration;
import kr.hanjari.backend.domain.enums.ClubCategory;

public record CommonClubDTO(String clubName, String leaderEmail, String category, String oneLiner, String briefIntroduction) {
    public static CommonClubDTO of(String clubName, String leaderEmail, String category, String oneLiner, String briefIntroduction) {
        return new CommonClubDTO(clubName, leaderEmail, category, oneLiner, briefIntroduction);
    }

    public ClubRegistration toClubRegistration() {
        return ClubRegistration.builder()
                .name(clubName())
                .category(ClubCategory.valueOf(category()))
                .leaderEmail(leaderEmail())
                .oneLiner(oneLiner())
                .briefIntroduction(briefIntroduction())
                .build();
    }
}

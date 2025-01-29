package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.Introduction;

public record ClubIntroductionDTO(
        ClubDTO club,
        String introduction,
        String activity,
        String recruitment
) {
    public static ClubIntroductionDTO of(Club club, Introduction introduction) {
        return new ClubIntroductionDTO(
                ClubDTO.of(club),
                introduction.getContent1(),
                introduction.getContent2(),
                introduction.getContent3()
        );
    }
}

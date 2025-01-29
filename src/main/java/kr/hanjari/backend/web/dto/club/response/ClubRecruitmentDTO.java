package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.Recruitment;

public record ClubRecruitmentDTO(
        ClubDTO club,
        String due,
        String notice,
        String etc
) {
    public static ClubRecruitmentDTO of(Recruitment recruitment, Club club) {
        return new ClubRecruitmentDTO(
                ClubDTO.of(club),
                recruitment.getContent1(),
                recruitment.getContent2(),
                recruitment.getContent3()
        );
    }

}

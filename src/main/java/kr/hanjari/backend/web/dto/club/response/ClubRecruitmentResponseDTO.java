package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.Recruitment;

public record ClubRecruitmentResponseDTO(
        ClubResponseDTO club,
        String due,
        String notice,
        String etc
) {
    public static ClubRecruitmentResponseDTO of(Recruitment recruitment, Club club) {
        return new ClubRecruitmentResponseDTO(
                ClubResponseDTO.of(club),
                recruitment.getContent1(),
                recruitment.getContent2(),
                recruitment.getContent3()
        );
    }

}

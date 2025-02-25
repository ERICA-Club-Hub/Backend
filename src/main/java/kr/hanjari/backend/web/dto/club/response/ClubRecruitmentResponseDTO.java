package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.Recruitment;

public record ClubRecruitmentResponseDTO(
        ClubResponseDTO club,
        String due,
        String notice,
        String etc
) {
    public static ClubRecruitmentResponseDTO of(Recruitment recruitment, Club club, String profileImageUrl) {
        if (recruitment == null) {
            return new ClubRecruitmentResponseDTO(
                    ClubResponseDTO.of(club, profileImageUrl),
                    null, null, null);
        }
        return new ClubRecruitmentResponseDTO(
                ClubResponseDTO.of(club, profileImageUrl),
                recruitment.getContent1(),
                recruitment.getContent2(),
                recruitment.getContent3()
        );
    }

}

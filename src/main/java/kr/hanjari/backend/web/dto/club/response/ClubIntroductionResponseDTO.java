package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.Introduction;

public record ClubIntroductionResponseDTO(
        ClubResponseDTO club,
        String introduction,
        String activity,
        String recruitment
) {
    public static ClubIntroductionResponseDTO of(Club club, Introduction introduction, String profileImageUrl) {
        if (introduction == null) {
            return new ClubIntroductionResponseDTO(
                    ClubResponseDTO.of(club, profileImageUrl),
                    null, null, null);
        }
        return new ClubIntroductionResponseDTO(
                ClubResponseDTO.of(club, profileImageUrl),
                introduction.getContent1(),
                introduction.getContent2(),
                introduction.getContent3()
        );
    }
}

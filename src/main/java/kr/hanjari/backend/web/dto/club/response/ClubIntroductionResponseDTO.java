package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.Introduction;

public record ClubIntroductionResponseDTO(
        String introduction,
        String activity
) {
    public static ClubIntroductionResponseDTO of(Introduction introduction) {
        if (introduction == null) {
            return new ClubIntroductionResponseDTO(
                    null, null);
        }
        return new ClubIntroductionResponseDTO(
                introduction.getContent1(),
                introduction.getContent2()
        );
    }
}

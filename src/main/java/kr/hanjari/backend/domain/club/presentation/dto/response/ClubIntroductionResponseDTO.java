package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.domain.entity.detail.Introduction;

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

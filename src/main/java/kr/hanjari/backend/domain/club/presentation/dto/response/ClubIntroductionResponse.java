package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.domain.entity.detail.Introduction;

public record ClubIntroductionResponse(
        String introduction,
        String activity
) {
    public static ClubIntroductionResponse of(Introduction introduction) {
        if (introduction == null) {
            return new ClubIntroductionResponse(
                    null, null);
        }
        return new ClubIntroductionResponse(
                introduction.getContent1(),
                introduction.getContent2()
        );
    }
}

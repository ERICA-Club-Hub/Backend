package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.domain.entity.detail.Recruitment;

public record ClubRecruitmentResponse(
        String due,
        String target,
        String notice,
        String etc
) {
    public static ClubRecruitmentResponse of(Recruitment recruitment, String target) {
        if (recruitment == null) {
            return new ClubRecruitmentResponse(null, null, null, null);
        }
        return new ClubRecruitmentResponse(
                recruitment.getContent1(),
                target,
                recruitment.getContent2(),
                recruitment.getContent3()
        );
    }

}

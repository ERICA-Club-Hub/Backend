package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.domain.entity.detail.Recruitment;

public record ClubRecruitmentResponseDTO(
        String due,
        String target,
        String notice,
        String etc
) {
    public static ClubRecruitmentResponseDTO of(Recruitment recruitment, String target) {
        if (recruitment == null) {
            return new ClubRecruitmentResponseDTO(null, null, null, null);
        }
        return new ClubRecruitmentResponseDTO(
                recruitment.getContent1(),
                target,
                recruitment.getContent2(),
                recruitment.getContent3()
        );
    }

}

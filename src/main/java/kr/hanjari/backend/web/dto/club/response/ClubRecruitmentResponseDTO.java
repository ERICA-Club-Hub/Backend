package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.Recruitment;

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

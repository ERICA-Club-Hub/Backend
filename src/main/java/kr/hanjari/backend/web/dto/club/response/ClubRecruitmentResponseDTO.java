package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.Recruitment;

public record ClubRecruitmentResponseDTO(
        String due,
        String notice,
        String etc
) {
    public static ClubRecruitmentResponseDTO of(Recruitment recruitment) {
        if (recruitment == null) {
            return new ClubRecruitmentResponseDTO(null, null, null);
        }
        return new ClubRecruitmentResponseDTO(
                recruitment.getContent1(),
                recruitment.getContent2(),
                recruitment.getContent3()
        );
    }

}

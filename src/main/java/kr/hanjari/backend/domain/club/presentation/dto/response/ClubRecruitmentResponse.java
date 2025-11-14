package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.detail.Recruitment;

@Schema(description = "DTO for club recruitment response")
public record ClubRecruitmentResponse(
        @Schema(description = "Recruitment due date", nullable = true, example = "2024-03-31")
        String due,
        @Schema(description = "Recruitment target", nullable = true, example = "Undergraduate students")
        String target,
        @Schema(description = "Recruitment notice", nullable = true, example = "Interview after document submission")
        String notice,
        @Schema(description = "Other recruitment information", nullable = true, example = "For further questions...")
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

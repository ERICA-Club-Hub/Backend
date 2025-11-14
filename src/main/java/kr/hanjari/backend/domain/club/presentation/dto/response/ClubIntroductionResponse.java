package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.detail.Introduction;

@Schema(description = "DTO for club introduction response")
public record ClubIntroductionResponse(
        @Schema(description = "Club introduction content", nullable = true, example = "Hanjari is...")
        String introduction,
        @Schema(description = "Club activity content", nullable = true, example = "Regular meeting every Monday")
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

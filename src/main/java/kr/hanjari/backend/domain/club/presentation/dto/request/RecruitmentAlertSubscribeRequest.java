package kr.hanjari.backend.domain.club.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for recruitment alert subscribe request")
public record RecruitmentAlertSubscribeRequest(
        @Schema(description = "Email to receive recruitment start alerts", example = "student@example.com")
        String email
) {
}

package kr.hanjari.backend.domain.club.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;

@Schema(description = "DTO for club detail request")
public record ClubDetailRequest(
        @NotNull(message = "Recruitment status is required.")
        @Schema(description = "Recruitment status", nullable = false, example = "RECRUITING", requiredMode = Schema.RequiredMode.REQUIRED)
        RecruitmentStatus recruitmentStatus,
        @NotBlank(message = "Leader's name is required.")
        @Schema(description = "Leader's name", nullable = false, example = "Gildong Hong", requiredMode = Schema.RequiredMode.REQUIRED)
        String leaderName,
        @NotBlank(message = "Leader's phone number is required.")
        @Schema(description = "Leader's phone number", nullable = false, example = "010-1234-5678", requiredMode = Schema.RequiredMode.REQUIRED)
        String leaderPhone,
        @NotBlank(message = "Activities are required.")
        @Schema(description = "Club activities", nullable = false, example = "Regular meeting every Monday", requiredMode = Schema.RequiredMode.REQUIRED)
        String activities,
        @NotNull(message = "Membership fee is required.")
        @PositiveOrZero(message = "Membership fee must be zero or positive.")
        @Schema(description = "Membership fee", nullable = false, example = "10000", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer membershipFee,
        @Schema(description = "SNS URL", example = "https://www.instagram.com/hanjari_")
        String snsUrl,
        @Schema(description = "Application URL", example = "https://forms.gle/...")
        String applicationUrl
) {
}

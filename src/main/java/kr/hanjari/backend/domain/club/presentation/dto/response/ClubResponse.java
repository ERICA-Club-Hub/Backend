package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.enums.ClubType;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;

@Schema(description = "DTO for club response")
public record ClubResponse(
        @Schema(description = "Club ID", nullable = false, example = "1")
        Long id,
        @Schema(description = "Club name", nullable = false, example = "Hanjari")
        String name,
        @Schema(description = "Club description (one-liner)", nullable = false, example = "The best central club at Hanyang University ERICA")
        String description,
        @Schema(description = "Recruitment status", nullable = false, example = "RECRUITING")
        RecruitmentStatus recruitmentStatus,
        @Schema(description = "Club profile image URL", nullable = true, example = "https://.../profile.png")
        String profileImageUrl,
        @Schema(description = "Club activities", nullable = true, example = "Regular meeting every Monday")
        String activities,
        @Schema(description = "Leader's name", nullable = true, example = "Gildong Hong")
        String leaderName,
        @Schema(description = "Leader's email", nullable = true, example = "leader@example.com")
        String leaderEmail,
        @Schema(description = "Leader's phone number", nullable = true, example = "010-1234-5678")
        String leaderPhone,
        @Schema(description = "Membership fee", nullable = true, example = "10000")
        Integer membershipFee,
        @Schema(description = "SNS URL", nullable = true, example = "https://www.instagram.com/hanjari_")
        String snsUrl,
        @Schema(description = "Application URL", nullable = true, example = "https://forms.gle/...")
        String applicationUrl,
        @Schema(description = "Club type", nullable = false, example = "CENTRAL")
        ClubType clubType
) {
    public static ClubResponse of(Club club, String profileImageUrl) {
        return new ClubResponse(
                club.getId(),
                club.getName(),
                club.getOneLiner(),
                club.getRecruitmentStatus(),
                profileImageUrl,
                club.getMeetingSchedule(),
                club.getLeaderName(),
                club.getLeaderEmail(),
                club.getLeaderPhone(),
                club.getMembershipFee(),
                club.getSnsUrl(),
                club.getApplicationUrl(),
                club.getCategoryInfo().getClubType()
        );
    }
}

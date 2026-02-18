package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;

@Schema(description = "DTO for service admin club detail response")
public record ClubAdminDetailResponse(
        @Schema(description = "Club ID", nullable = false, example = "1")
        Long id,
        @Schema(description = "Club name", nullable = false, example = "Hanjari")
        String name,
        @Schema(description = "Club description (one-liner)", nullable = false, example = "The best central club at Hanyang University ERICA")
        String oneLiner,
        @Schema(description = "Recruitment status", nullable = false, example = "RECRUITING")
        RecruitmentStatus recruitmentStatus,
        @Schema(description = "Club profile image URL", nullable = true, example = "https://.../profile.png")
        String profileImageUrl,
        @Schema(description = "Application URL", nullable = true, example = "https://forms.gle/...")
        String applicationUrl,
        @Schema(description = "Club category information", nullable = false)
        CategoryResponse category,
        @Schema(description = "Club category tag", nullable = false, example = "Academic")
        String tag,
        @Schema(description = "Leader email", nullable = true, example = "leader@example.com")
        String leaderEmail,
        @Schema(description = "Club description", nullable = true, example = "We are a club that...")
        String description
) {
    public static ClubAdminDetailResponse of(Club club, String profileImageUrl) {
        return new ClubAdminDetailResponse(
                club.getId(),
                club.getName(),
                club.getOneLiner(),
                club.getRecruitmentStatus(),
                profileImageUrl,
                club.getApplicationUrl(),
                CategoryResponse.from(club.getCategoryInfo()),
                CategoryResponse.getTag(club.getCategoryInfo()),
                club.getLeaderEmail(),
                club.getDescription()
        );
    }
}

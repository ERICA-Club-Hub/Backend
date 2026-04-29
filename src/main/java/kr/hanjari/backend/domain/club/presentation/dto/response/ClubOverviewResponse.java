package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.Club;

@Schema(description = "DTO for club overview response")
public record ClubOverviewResponse(
        @Schema(description = "Club ID", nullable = false, example = "1")
        Long id,
        @Schema(description = "Club name", nullable = false, example = "Hanjari")
        String name,
        @Schema(description = "Club description (one-liner)", nullable = false, example = "The best central club at Hanyang University ERICA")
        String oneLiner,
        @Schema(description = "Club tags (max 2, ordered by priority)", nullable = false)
        List<String> tags,
        @Schema(description = "Club profile image URL", nullable = true, example = "https://.../profile.png")
        String profileImageUrl,
        @Schema(description = "Application URL", nullable = true, example = "https://forms.gle/...")
        String applicationUrl,
        @Schema(description = "Club category information", nullable = false)
        CategoryResponse category,
        @Schema(description = "Club category tag", nullable = false, example = "Academic")
        String tag
) {
    public static ClubOverviewResponse of(Club club, String profileImageUrl, List<String> tags) {
        return new ClubOverviewResponse(
                club.getId(),
                club.getName(),
                club.getOneLiner(),
                tags,
                profileImageUrl,
                club.getApplicationUrl(),
                CategoryResponse.from(club.getCategoryInfo()),
                CategoryResponse.getTag(club.getCategoryInfo())
        );
    }
}
package kr.hanjari.backend.domain.activity.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.Club;

@Schema(description = "DTO for recent activity log response")
public record RecentActivityLogResponse(
        @Schema(description = "List of recent activity logs", nullable = false)
        List<RecentActivityLog> activityLogs
) {

    public static RecentActivityLogResponse of(List<RecentActivityLog> activityLogs) {
        return new RecentActivityLogResponse(activityLogs);
    }

    @Schema(description = "Recent activity log")
    public record RecentActivityLog(
            @Schema(description = "Activity image URL", nullable = false, example = "https://.../activity.png")
            String imageUrl,
            @Schema(description = "Club ID", nullable = false, example = "1")
            Long clubId,
            @Schema(description = "Club name", nullable = false, example = "Hanjari")
            String clubName,
            @Schema(description = "Club profile image URL", nullable = true, example = "https://.../club_profile.png")
            String clubProfileImageUrl
    ) {

        public static RecentActivityLog of(String imageUrl, Club club, String clubProfileImageUrl) {
            return new RecentActivityLog(imageUrl, club.getId(), club.getName(), clubProfileImageUrl);
        }

    }
}

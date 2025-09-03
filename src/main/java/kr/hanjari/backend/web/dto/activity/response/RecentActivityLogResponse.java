package kr.hanjari.backend.web.dto.activity.response;

import java.util.List;
import kr.hanjari.backend.domain.Club;

public record RecentActivityLogResponse(List<RecentActivityLog> activityLogs) {

    public static RecentActivityLogResponse of(List<RecentActivityLog> activityLogs) {
        return new RecentActivityLogResponse(activityLogs);
    }

    public record RecentActivityLog(
            String imageUrl,
            long clubId,
            String clubName,
            String clubProfileImageUrl
    ) {

        public static RecentActivityLog of(String imageUrl, Club club, String clubProfileImageUrl) {
            return new RecentActivityLog(imageUrl, club.getId(), club.getName(), clubProfileImageUrl);
        }

    }
}

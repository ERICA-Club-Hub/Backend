package kr.hanjari.backend.domain.announcement.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.announcement.domain.entity.Announcement;

import java.time.LocalDate;

@Schema(description = "DTO for announcement")
public record AnnouncementDTO(
        @Schema(description = "Announcement ID", nullable = false, example = "1")
        Long announcementId,
        @Schema(description = "Announcement title", nullable = false, example = "Recruiting new members for the first semester of 2024")
        String title,
        @Schema(description = "Announcement date", nullable = false, example = "2024-03-01")
        LocalDate date,
        @Schema(description = "Announcement URL", nullable = false, example = "https://www.hanjari.kr/announcement/1")
        String url,
        @Schema(description = "Thumbnail image URL", nullable = true, example = "https://.../thumbnail.png")
        String thumbnailUrl
) {
    public static AnnouncementDTO of(Announcement announcement, String thumbnailUrl) {
        return new AnnouncementDTO(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getCreatedAt().toLocalDate(),
                announcement.getUrl(),
                thumbnailUrl
        );
    }
}

package kr.hanjari.backend.domain.announcement.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import kr.hanjari.backend.domain.announcement.domain.entity.Announcement;

@Schema(description = "DTO for creating/updating an announcement")
public record AnnouncementRequest(
        @NotBlank(message = "Title is required.")
        @Schema(description = "The title of the announcement", nullable = false, example = "Recruiting new members for the first semester of 2024", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @NotBlank(message = "URL is required.")
        @Schema(description = "The URL of the announcement", nullable = false, example = "https://www.hanjari.kr/announcement/1", requiredMode = Schema.RequiredMode.REQUIRED)
        String url
) {
    public Announcement toAnnouncement() {
        return Announcement.builder()
                .title(title)
                .url(url)
                .build();
    }
}

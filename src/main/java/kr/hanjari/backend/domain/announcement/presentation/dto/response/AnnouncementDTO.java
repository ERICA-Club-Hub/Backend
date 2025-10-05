package kr.hanjari.backend.domain.announcement.presentation.dto.response;

import kr.hanjari.backend.domain.announcement.domain.entity.Announcement;

import java.time.LocalDate;

public record AnnouncementDTO(
        Long announcementId,
        String title,
        LocalDate date,
        String url,
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

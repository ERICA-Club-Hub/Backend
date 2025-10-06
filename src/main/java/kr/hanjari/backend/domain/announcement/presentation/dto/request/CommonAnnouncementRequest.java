package kr.hanjari.backend.domain.announcement.presentation.dto.request;

import kr.hanjari.backend.domain.announcement.domain.entity.Announcement;

public record CommonAnnouncementRequest(
        String title,
        String url
) {
    public Announcement toAnnouncement() {
        return Announcement.builder()
                .title(title)
                .url(url)
                .build();
    }
}

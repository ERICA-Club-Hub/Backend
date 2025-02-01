package kr.hanjari.backend.web.dto.announcement.request;

import kr.hanjari.backend.domain.Announcement;

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

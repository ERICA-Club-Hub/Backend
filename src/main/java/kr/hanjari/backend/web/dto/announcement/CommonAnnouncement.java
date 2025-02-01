package kr.hanjari.backend.web.dto.announcement;

import kr.hanjari.backend.domain.Announcement;

public record CommonAnnouncement(
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

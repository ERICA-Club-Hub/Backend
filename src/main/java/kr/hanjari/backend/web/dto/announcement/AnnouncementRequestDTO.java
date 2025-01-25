package kr.hanjari.backend.web.dto.announcement;

import lombok.Getter;

public class AnnouncementRequestDTO {

    @Getter
    public static class CommonAnnouncement {
        private String title;
        private String url;
    }


}

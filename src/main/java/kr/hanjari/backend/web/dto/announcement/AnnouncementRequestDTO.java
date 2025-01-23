package kr.hanjari.backend.web.dto.announcement;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class AnnouncementRequestDTO {

    @Getter
    public static class CommonAnnouncement {
        private MultipartFile thumbnail;
        private String title;
        private String url;
    }


}

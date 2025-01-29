package kr.hanjari.backend.web.dto.serviceAnnouncement.response;

import java.time.LocalDateTime;
import kr.hanjari.backend.domain.ServiceAnnouncement;

public record ServiceAnnouncementDetailDTO(
        Long id, String title, String content, LocalDateTime createdAt) {

    public static ServiceAnnouncementDetailDTO of(ServiceAnnouncement serviceAnnouncement) {
        return new ServiceAnnouncementDetailDTO(
                serviceAnnouncement.getId(),
                serviceAnnouncement.getTitle(),
                serviceAnnouncement.getContent(),
                serviceAnnouncement.getCreatedAt()
        );
    }
}

package kr.hanjari.backend.web.dto.serviceAnnouncement;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class ServiceAnnouncementResponseDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceAnnouncementDetailDTO {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdAt;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceAnnouncementSearchDTO {
        private List<ServiceAnnouncementDetailDTO> serviceAnnouncements;
        private int totalElements;
    }
}

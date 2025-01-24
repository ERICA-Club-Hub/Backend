package kr.hanjari.backend.web.dto.serviceAnnouncement;

import java.time.LocalDateTime;
import java.util.List;
import kr.hanjari.backend.domain.ServiceAnnouncement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

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

        public static ServiceAnnouncementDetailDTO of(ServiceAnnouncement serviceAnnouncement) {
            return ServiceAnnouncementDetailDTO.builder()
                    .id(serviceAnnouncement.getId())
                    .title(serviceAnnouncement.getTitle())
                    .content(serviceAnnouncement.getContent())
                    .createdAt(serviceAnnouncement.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceAnnouncementSearchDTO {
        private List<ServiceAnnouncementDetailDTO> serviceAnnouncements;
        private int totalElements;

        public static ServiceAnnouncementSearchDTO of(Page<ServiceAnnouncement> serviceAnnouncements) {
            return ServiceAnnouncementSearchDTO.builder()
                    .serviceAnnouncements(serviceAnnouncements.map(ServiceAnnouncementDetailDTO::of).getContent())
                    .totalElements((int) serviceAnnouncements.getTotalElements())
                    .build();
        }
    }
}

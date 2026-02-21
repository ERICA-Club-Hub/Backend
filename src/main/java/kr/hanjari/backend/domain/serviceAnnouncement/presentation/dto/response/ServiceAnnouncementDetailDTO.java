package kr.hanjari.backend.domain.serviceAnnouncement.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import kr.hanjari.backend.domain.serviceAnnouncement.domain.entity.ServiceAnnouncement;

@Schema(description = "DTO for service announcement details")
public record ServiceAnnouncementDetailDTO(
        @Schema(description = "Service announcement ID", nullable = false, example = "1")
        Long id,
        @Schema(description = "Service announcement title", nullable = false, example = "Server Maintenance Notice")
        String title,
        @Schema(description = "Service announcement content", nullable = false, example = "There will be a server maintenance from midnight.")
        String content,
        @Schema(description = "Creation timestamp", nullable = false, example = "2024-03-01T10:00:00")
        LocalDateTime createdAt
) {

    public static ServiceAnnouncementDetailDTO of(ServiceAnnouncement serviceAnnouncement) {
        return new ServiceAnnouncementDetailDTO(
                serviceAnnouncement.getId(),
                serviceAnnouncement.getTitle(),
                serviceAnnouncement.getContent(),
                serviceAnnouncement.getCreatedAt()
        );
    }
}

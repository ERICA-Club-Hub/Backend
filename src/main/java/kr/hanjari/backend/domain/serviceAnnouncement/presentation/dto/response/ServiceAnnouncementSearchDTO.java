package kr.hanjari.backend.domain.serviceAnnouncement.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import kr.hanjari.backend.domain.serviceAnnouncement.domain.entity.ServiceAnnouncement;
import org.springframework.data.domain.Page;

@Schema(description = "DTO for service announcement search response")
public record ServiceAnnouncementSearchDTO(
        @Schema(description = "List of service announcement details", nullable = false)
        List<ServiceAnnouncementDetailDTO> serviceAnnouncements,
        @Schema(description = "Total number of elements", nullable = false, example = "10")
        Integer totalElements
) {
    public static ServiceAnnouncementSearchDTO of(Page<ServiceAnnouncement> serviceAnnouncements) {
        return new ServiceAnnouncementSearchDTO(
                serviceAnnouncements.map(ServiceAnnouncementDetailDTO::of).getContent(),
                serviceAnnouncements.getNumberOfElements()
        );
    }
}

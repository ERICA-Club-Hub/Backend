package kr.hanjari.backend.domain.serviceAnnouncement.presentation.dto.response;

import java.util.List;
import kr.hanjari.backend.domain.serviceAnnouncement.domain.entity.ServiceAnnouncement;
import org.springframework.data.domain.Page;

public record ServiceAnnouncementSearchDTO(
        List<ServiceAnnouncementDetailDTO> serviceAnnouncements,
        Integer totalElements
) {
    public static ServiceAnnouncementSearchDTO of(Page<ServiceAnnouncement> serviceAnnouncements) {
        return new ServiceAnnouncementSearchDTO(
                serviceAnnouncements.map(ServiceAnnouncementDetailDTO::of).getContent(),
                serviceAnnouncements.getNumberOfElements()
        );
    }
}

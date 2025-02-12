package kr.hanjari.backend.web.dto.serviceAnnouncement.response;

import java.util.List;
import kr.hanjari.backend.domain.ServiceAnnouncement;
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

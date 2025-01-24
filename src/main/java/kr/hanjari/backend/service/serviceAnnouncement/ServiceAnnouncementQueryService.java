package kr.hanjari.backend.service.serviceAnnouncement;

import kr.hanjari.backend.web.dto.serviceAnnouncement.ServiceAnnouncementResponseDTO;
import kr.hanjari.backend.web.dto.serviceAnnouncement.ServiceAnnouncementResponseDTO.ServiceAnnouncementSearchDTO;

public interface ServiceAnnouncementQueryService {

    ServiceAnnouncementSearchDTO getAllServiceAnnouncements(Integer page, Integer size);

    ServiceAnnouncementResponseDTO.ServiceAnnouncementDetailDTO getServiceAnnouncement(Long id);

}

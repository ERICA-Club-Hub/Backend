package kr.hanjari.backend.service.serviceAnnouncement;

import kr.hanjari.backend.web.dto.serviceAnnouncement.response.ServiceAnnouncementDetailDTO;
import kr.hanjari.backend.web.dto.serviceAnnouncement.response.ServiceAnnouncementSearchDTO;

public interface ServiceAnnouncementQueryService {

    ServiceAnnouncementSearchDTO getAllServiceAnnouncements(Integer page, Integer size);

    ServiceAnnouncementDetailDTO getServiceAnnouncement(Long id);

}

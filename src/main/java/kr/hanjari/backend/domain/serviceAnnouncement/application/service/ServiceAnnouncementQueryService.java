package kr.hanjari.backend.domain.serviceAnnouncement.application.service;

import kr.hanjari.backend.domain.serviceAnnouncement.presentation.dto.response.ServiceAnnouncementDetailDTO;
import kr.hanjari.backend.domain.serviceAnnouncement.presentation.dto.response.ServiceAnnouncementSearchDTO;

public interface ServiceAnnouncementQueryService {

    ServiceAnnouncementSearchDTO getAllServiceAnnouncements(Integer page, Integer size);

    ServiceAnnouncementDetailDTO getServiceAnnouncement(Long id);

}

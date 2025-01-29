package kr.hanjari.backend.service.serviceAnnouncement;

import kr.hanjari.backend.web.dto.serviceAnnouncement.request.CreateServiceAnnouncementRequestDTO;

public interface ServiceAnnouncementCommandService {

    Long createServiceAnnouncement(CreateServiceAnnouncementRequestDTO requestDTO);

    Long updateServiceAnnouncement(Long id, CreateServiceAnnouncementRequestDTO requestDTO);

    void deleteServiceAnnouncement(Long id);
}

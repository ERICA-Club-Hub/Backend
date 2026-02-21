package kr.hanjari.backend.domain.serviceAnnouncement.application.service;

import kr.hanjari.backend.domain.serviceAnnouncement.presentation.dto.request.CreateServiceAnnouncementRequestDTO;

public interface ServiceAnnouncementCommandService {

    Long createServiceAnnouncement(CreateServiceAnnouncementRequestDTO requestDTO);

    Long updateServiceAnnouncement(Long id, CreateServiceAnnouncementRequestDTO requestDTO);

    void deleteServiceAnnouncement(Long id);
}

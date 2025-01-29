package kr.hanjari.backend.service.serviceAnnouncement.impl;

import kr.hanjari.backend.domain.ServiceAnnouncement;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.ServiceAnnouncementRepository;
import kr.hanjari.backend.service.serviceAnnouncement.ServiceAnnouncementCommandService;
import kr.hanjari.backend.web.dto.serviceAnnouncement.request.CreateServiceAnnouncementRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServiceAnnouncementCommandServiceImpl implements ServiceAnnouncementCommandService {

    private final ServiceAnnouncementRepository serviceAnnouncementRepository;

    @Override
    public Long createServiceAnnouncement(CreateServiceAnnouncementRequestDTO requestDTO) {
        ServiceAnnouncement serviceAnnouncement = ServiceAnnouncement.builder()
                .title(requestDTO.title())
                .content(requestDTO.content())
                .build();

        ServiceAnnouncement save = serviceAnnouncementRepository.save(serviceAnnouncement);
        return save.getId();
    }

    @Override
    public Long updateServiceAnnouncement(Long id, CreateServiceAnnouncementRequestDTO requestDTO) {
        ServiceAnnouncement serviceAnnouncement = serviceAnnouncementRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus._SERVICE_ANNOUNCEMENT_NOT_FOUND));

        serviceAnnouncement.update(requestDTO.title(), requestDTO.content());

        ServiceAnnouncement save = serviceAnnouncementRepository.save(serviceAnnouncement);
        return save.getId();
    }

    @Override
    public void deleteServiceAnnouncement(Long id) {
        ServiceAnnouncement serviceAnnouncement = serviceAnnouncementRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus._SERVICE_ANNOUNCEMENT_NOT_FOUND));

        serviceAnnouncementRepository.delete(serviceAnnouncement);
    }
}

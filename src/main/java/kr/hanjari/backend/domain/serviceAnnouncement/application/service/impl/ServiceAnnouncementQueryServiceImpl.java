package kr.hanjari.backend.domain.serviceAnnouncement.application.service.impl;

import kr.hanjari.backend.domain.serviceAnnouncement.domain.entity.ServiceAnnouncement;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.domain.serviceAnnouncement.domain.repository.ServiceAnnouncementRepository;
import kr.hanjari.backend.domain.serviceAnnouncement.application.service.ServiceAnnouncementQueryService;
import kr.hanjari.backend.domain.serviceAnnouncement.presentation.dto.response.ServiceAnnouncementDetailDTO;
import kr.hanjari.backend.domain.serviceAnnouncement.presentation.dto.response.ServiceAnnouncementSearchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServiceAnnouncementQueryServiceImpl implements ServiceAnnouncementQueryService {

    private final ServiceAnnouncementRepository serviceAnnouncementRepository;

    @Override
    public ServiceAnnouncementSearchDTO getAllServiceAnnouncements(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<ServiceAnnouncement> all = serviceAnnouncementRepository.findAll(pageable);
        if (all.isEmpty()) throw new GeneralException(ErrorStatus._SERVICE_ANNOUNCEMENT_NOT_FOUND);

        return ServiceAnnouncementSearchDTO.of(all);
    }

    @Override
    public ServiceAnnouncementDetailDTO getServiceAnnouncement(Long id) {
        ServiceAnnouncement serviceAnnouncement = serviceAnnouncementRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus._SERVICE_ANNOUNCEMENT_NOT_FOUND));
        return ServiceAnnouncementDetailDTO.of(serviceAnnouncement);
    }
}

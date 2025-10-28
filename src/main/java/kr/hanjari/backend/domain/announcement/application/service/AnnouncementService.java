package kr.hanjari.backend.domain.announcement.application.service;

import kr.hanjari.backend.domain.announcement.presentation.dto.request.AnnouncementRequest;
import kr.hanjari.backend.domain.announcement.presentation.dto.response.AnnouncementCommandResponse;
import kr.hanjari.backend.domain.announcement.presentation.dto.response.GetAllAnnouncementResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AnnouncementService {
    AnnouncementCommandResponse createAnnouncement(AnnouncementRequest commonAnnouncementRequest, MultipartFile thumbnail);
    AnnouncementCommandResponse updateAnnouncement(Long announcementId, AnnouncementRequest commonAnnouncementRequest, MultipartFile thumbnail);
    void deleteAnnouncement(Long announcementId);
    GetAllAnnouncementResponse getAllAnnouncement();
}

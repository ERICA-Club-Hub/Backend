package kr.hanjari.backend.service.announcement;

import kr.hanjari.backend.web.dto.announcement.CommonAnnouncement;
import kr.hanjari.backend.web.dto.announcement.request.CommonAnnouncementRequest;
import kr.hanjari.backend.web.dto.announcement.response.GetAllAnnouncementResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AnnouncementService {
    Long createAnnouncement(CommonAnnouncementRequest commonAnnouncementRequest, MultipartFile thumbnail);
    void updateAnnouncement(Long announcementId, CommonAnnouncementRequest commonAnnouncementRequest, MultipartFile thumbnail);
    void deleteAnnouncement(Long announcementId);
    GetAllAnnouncementResponse getAllAnnouncement();
}

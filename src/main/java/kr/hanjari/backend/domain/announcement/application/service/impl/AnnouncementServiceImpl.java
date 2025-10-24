package kr.hanjari.backend.domain.announcement.application.service.impl;

import kr.hanjari.backend.domain.announcement.domain.entity.Announcement;
import kr.hanjari.backend.domain.file.domain.entity.File;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.domain.announcement.domain.repository.AnnouncementRepository;
import kr.hanjari.backend.domain.announcement.application.service.AnnouncementService;
import kr.hanjari.backend.infrastructure.s3.S3Service;
import kr.hanjari.backend.domain.announcement.presentation.dto.request.CommonAnnouncementRequest;
import kr.hanjari.backend.domain.announcement.presentation.dto.AnnouncementDTO;
import kr.hanjari.backend.domain.announcement.presentation.dto.response.GetAllAnnouncementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final S3Service s3Service;

    public Long createAnnouncement(CommonAnnouncementRequest commonAnnouncementRequest, MultipartFile thumbnail) {

        Announcement newAnnouncement = commonAnnouncementRequest.toAnnouncement();
        File thumbnailImage = s3Service.uploadFile(thumbnail);

        newAnnouncement.updateThumbnailImage(thumbnailImage);

        announcementRepository.save(newAnnouncement);
        return newAnnouncement.getId();
    }

    public void updateAnnouncement(Long announcementId, CommonAnnouncementRequest commonAnnouncementRequest, MultipartFile thumbnail) {

        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._UNION_ANNOUNCEMENT_NOT_FOUND));

        announcement.updateTitleAndUrl(commonAnnouncementRequest.title(), commonAnnouncementRequest.url());

        if (thumbnail != null) {
            File fileToDelete = announcement.getThumbnailImage();
            File newThumbnailImage = s3Service.uploadFile(thumbnail);
            announcement.updateThumbnailImage(newThumbnailImage);
            announcementRepository.save(announcement);
            s3Service.deleteFile(fileToDelete.getId());
        }
    }

    public void deleteAnnouncement(Long announcementId) {

        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._UNION_ANNOUNCEMENT_NOT_FOUND));

        File fileToDelete = announcement.getThumbnailImage();
        announcementRepository.delete(announcement);
        s3Service.deleteFile(fileToDelete.getId());
    }

    public GetAllAnnouncementResponse getAllAnnouncement() {
        List<Announcement> announcementList = announcementRepository.findAll();

        return GetAllAnnouncementResponse.of(
                announcementList.stream()
                        .map(announcement -> {
                            String url = s3Service.getDownloadUrl(announcement.getThumbnailImage().getId());
                            return AnnouncementDTO.of(announcement, url);
                        })
                        .toList());
    }
}

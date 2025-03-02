package kr.hanjari.backend.service.announcement.impl;

import kr.hanjari.backend.domain.Announcement;
import kr.hanjari.backend.domain.File;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.AnnouncementRepository;
import kr.hanjari.backend.security.token.JwtTokenProvider;
import kr.hanjari.backend.service.announcement.AnnouncementService;
import kr.hanjari.backend.service.s3.S3Service;
import kr.hanjari.backend.web.dto.announcement.request.CommonAnnouncementRequest;
import kr.hanjari.backend.web.dto.announcement.response.AnnouncementDTO;
import kr.hanjari.backend.web.dto.announcement.response.GetAllAnnouncementResponse;
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
                .orElseThrow(() -> new GeneralException(ErrorStatus._BAD_REQUEST)); // TODO: 예외

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
                .orElseThrow(() -> new GeneralException(ErrorStatus._BAD_REQUEST));

        s3Service.deleteFile(announcement.getThumbnailImage().getId());
        announcementRepository.delete(announcement);
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

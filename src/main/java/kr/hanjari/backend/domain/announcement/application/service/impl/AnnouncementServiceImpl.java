package kr.hanjari.backend.domain.announcement.application.service.impl;

import kr.hanjari.backend.domain.announcement.domain.entity.Announcement;
import kr.hanjari.backend.domain.announcement.presentation.dto.response.AnnouncementCommandResponse;
import kr.hanjari.backend.domain.file.application.FileService;
import kr.hanjari.backend.domain.file.domain.entity.File;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.domain.announcement.domain.repository.AnnouncementRepository;
import kr.hanjari.backend.domain.announcement.application.service.AnnouncementService;
import kr.hanjari.backend.domain.announcement.presentation.dto.request.AnnouncementRequest;
import kr.hanjari.backend.domain.announcement.presentation.dto.AnnouncementDTO;
import kr.hanjari.backend.domain.announcement.presentation.dto.response.GetAllAnnouncementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    private final FileService fileService;

    @Transactional
    public AnnouncementCommandResponse createAnnouncement(AnnouncementRequest request, MultipartFile imageFile) {

        Announcement newAnnouncement = request.toAnnouncement();

        Long fileId = fileService.uploadObjectAndSaveFile(imageFile);
        File thumbnail = fileService.getReferenceById(fileId);

        newAnnouncement.updateThumbnailImage(thumbnail);

        return AnnouncementCommandResponse.of(save(newAnnouncement).getId());
    }

    @Transactional
    public AnnouncementCommandResponse updateAnnouncement(Long announcementId, AnnouncementRequest announcementRequest, MultipartFile imageFile) {

        Announcement announcementToUpdate = getEntityById(announcementId);

        String title = announcementRequest.title();
        String url = announcementRequest.url();
        announcementToUpdate.update(title, url);

        if (imageFile != null) {
            File fileToDelete = announcementToUpdate.getThumbnailImage();

            Long fileId = fileService.uploadObjectAndSaveFile(imageFile);
            File thumbnail = fileService.getReferenceById(fileId);
            announcementToUpdate.updateThumbnailImage(thumbnail);

            fileService.deleteObjectAndFile(fileToDelete.getId());
        }

        return AnnouncementCommandResponse.of(save(announcementToUpdate).getId());

    }

    @Transactional
    public void deleteAnnouncement(Long announcementId) {

        Announcement announcementToDelete = getEntityById(announcementId);

        File fileToDelete = announcementToDelete.getThumbnailImage();
        fileService.deleteObjectAndFile(fileToDelete.getId());

        delete(announcementToDelete.getId());
    }

    public GetAllAnnouncementResponse getAllAnnouncement() {
        List<Announcement> announcementList = getAllEntity();

        return GetAllAnnouncementResponse.of(
                announcementList.stream()
                        .map(announcement -> {
                            Long fileId = announcement.getThumbnailImage().getId();
                            String url = fileService.getFileDownloadUrl(fileId);
                            return AnnouncementDTO.of(announcement, url);
                        })
                        .toList());
    }

    private Announcement save(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    private void delete(Long announcementId) {
        announcementRepository.deleteById(announcementId);
    }

    private Announcement getEntityById(Long announcementId) {
        return announcementRepository.findById(announcementId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._UNION_ANNOUNCEMENT_NOT_FOUND));
    }

    private List<Announcement> getAllEntity() {
        return announcementRepository.findAll();
    }
}

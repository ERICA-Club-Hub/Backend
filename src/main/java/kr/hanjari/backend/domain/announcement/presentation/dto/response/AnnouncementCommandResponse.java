package kr.hanjari.backend.domain.announcement.presentation.dto.response;

public record AnnouncementCommandResponse(
        Long announcementId
) {
    public static AnnouncementCommandResponse of(Long announcementId) {
        return new AnnouncementCommandResponse(announcementId);
    }
}

package kr.hanjari.backend.domain.announcement.presentation.dto.response;

import java.util.List;

public record GetAllAnnouncementResponse(
        List<AnnouncementDTO> announcementDTOList
) {
    public static GetAllAnnouncementResponse of(List<AnnouncementDTO> announcementDTOList) {
        return new GetAllAnnouncementResponse(announcementDTOList);
    }
}

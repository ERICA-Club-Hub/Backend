package kr.hanjari.backend.domain.announcement.presentation.dto.response;

import kr.hanjari.backend.domain.announcement.presentation.dto.AnnouncementDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "DTO for getting all announcements response")
public record GetAllAnnouncementResponse(
        @Schema(description = "List of announcement DTOs", nullable = false)
        List<AnnouncementDTO> announcementDTOList
) {
    public static GetAllAnnouncementResponse of(List<AnnouncementDTO> announcementDTOList) {
        return new GetAllAnnouncementResponse(announcementDTOList);
    }
}

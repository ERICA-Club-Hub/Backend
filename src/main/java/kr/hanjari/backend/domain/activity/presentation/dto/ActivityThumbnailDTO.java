package kr.hanjari.backend.domain.activity.presentation.dto;

import kr.hanjari.backend.domain.file.domain.dto.FileDownloadDTO;

public record ActivityThumbnailDTO(
        Long activityId,
        FileDownloadDTO fileDownloadDTO
) {
    public static ActivityThumbnailDTO of(Long activityId, FileDownloadDTO fileDownloadDTO) {
        return new ActivityThumbnailDTO(activityId, fileDownloadDTO);
    }
}

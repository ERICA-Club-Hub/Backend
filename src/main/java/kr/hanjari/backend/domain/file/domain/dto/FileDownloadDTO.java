package kr.hanjari.backend.domain.file.domain.dto;

import kr.hanjari.backend.domain.file.domain.entity.File;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FileDownloadDTO(
        Long fileId,
        String fileName,
        String extension,
        Long size,
        LocalDateTime updatedAt,
        String downloadUrl
) {

    public static FileDownloadDTO of(File file, String downloadUrl) {
        return FileDownloadDTO.builder()
                .fileId(file.getId())
                .fileName(file.getName())
                .extension(file.getExtension())
                .size(file.getSize())
                .updatedAt(file.getUpdatedAt())
                .downloadUrl(downloadUrl)
                .build();
    }

}

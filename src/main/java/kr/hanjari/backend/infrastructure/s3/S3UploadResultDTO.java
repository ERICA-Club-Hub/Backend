package kr.hanjari.backend.infrastructure.s3;

import kr.hanjari.backend.domain.file.domain.entity.File;

public record S3UploadResultDTO(
    String fileName,
    String fileKey,
    String fileExtension,
    Long fileSize
) {

    public static S3UploadResultDTO of(String fileName, String fileKey, String fileExtension, Long fileSize) {
        return new S3UploadResultDTO(fileName, fileKey, fileExtension, fileSize);
    }

    public File toFile() {
        return File.builder()
                .name(fileName)
                .fileKey(fileKey)
                .extension(fileExtension)
                .size(fileSize)
                .build();
    }
}

package kr.hanjari.backend.domain.file.domain.dto;

public record FileDTO(
        String fileName,
        String downloadUrl
) {
    public static FileDTO of(String fileName, String downloadUrl) {
        return new FileDTO(fileName, downloadUrl);
    }
}

package kr.hanjari.backend.web.dto.file;

public record FileDTO(
        String fileName,
        String downloadUrl
) {
    public static FileDTO of(String fileName, String downloadUrl) {
        return new FileDTO(fileName, downloadUrl);
    }
}

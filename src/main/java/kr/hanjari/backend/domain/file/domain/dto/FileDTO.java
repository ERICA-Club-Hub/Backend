package kr.hanjari.backend.domain.file.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for file information")
public record FileDTO(
        @Schema(description = "File name", nullable = false, example = "document.pdf")
        String fileName,
        @Schema(description = "Download URL of the file", nullable = false, example = "https://.../document.pdf")
        String downloadUrl
) {
    public static FileDTO of(String fileName, String downloadUrl) {
        return new FileDTO(fileName, downloadUrl);
    }
}

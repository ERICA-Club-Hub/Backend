package kr.hanjari.backend.domain.document.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.document.domain.entity.Document;

import java.time.LocalDate;

@Schema(description = "DTO for document")
public record DocumentDTO(
        @Schema(description = "Document ID", nullable = false, example = "1")
        Long id,
        @Schema(description = "Document title", nullable = false, example = "Club Rules")
        String title,
        @Schema(description = "Document creation date", nullable = false, example = "2024-03-01")
        LocalDate date
) {
    public static DocumentDTO from(Document document) {
        return new DocumentDTO(
                document.getId(),
                document.getTitle(),
                document.getCreatedAt().toLocalDate()
        );
    }
}

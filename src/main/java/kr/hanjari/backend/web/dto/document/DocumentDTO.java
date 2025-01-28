package kr.hanjari.backend.web.dto.document;

import kr.hanjari.backend.domain.Document;

import java.time.LocalDate;

public record DocumentDTO(
        Long id,
        String title,
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

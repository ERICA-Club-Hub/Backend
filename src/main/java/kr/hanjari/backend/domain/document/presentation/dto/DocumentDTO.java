package kr.hanjari.backend.domain.document.presentation.dto;

import kr.hanjari.backend.domain.document.domain.entity.Document;

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

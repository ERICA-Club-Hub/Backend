package kr.hanjari.backend.domain.document.presentation.dto.request;

import kr.hanjari.backend.domain.document.domain.entity.Document;

public record CreateDocumentRequest(
        String title
) {
    public Document toEntity() {
        return Document.builder()
                .title(title)
                .build();
    }
}

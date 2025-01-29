package kr.hanjari.backend.web.dto.document.request;

import kr.hanjari.backend.domain.Document;

public record CreateDocumentRequest(
        String title
) {
    public Document toEntity() {
        return Document.builder()
                .title(title)
                .build();
    }
}

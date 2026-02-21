package kr.hanjari.backend.domain.document.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import kr.hanjari.backend.domain.document.domain.entity.Document;

@Schema(description = "DTO for creating a document")
public record CreateDocumentRequest(
        @NotBlank(message = "Title is required.")
        @Schema(description = "Document title", nullable = false, example = "Club Rules", requiredMode = Schema.RequiredMode.REQUIRED)
        String title
) {
    public Document toEntity() {
        return Document.builder()
                .title(title)
                .build();
    }
}

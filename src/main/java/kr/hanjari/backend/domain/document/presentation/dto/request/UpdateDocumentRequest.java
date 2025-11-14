package kr.hanjari.backend.domain.document.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(description = "DTO for updating a document")
public record UpdateDocumentRequest(
        @NotBlank(message = "Title is required.")
        @Schema(description = "Document title", nullable = false, example = "Club Rules", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @Schema(description = "List of file IDs to be removed", example = "[1, 2, 3]")
        List<Long> removedFileIdList
) {
}

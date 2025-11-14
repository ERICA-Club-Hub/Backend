package kr.hanjari.backend.domain.document.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.document.presentation.dto.DocumentDTO;

import java.util.List;

@Schema(description = "DTO for getting all documents response")
public record GetAllDocumentsResponse(
        @Schema(description = "List of document DTOs", nullable = false)
        List<DocumentDTO> documentDTOList
) {
    public static GetAllDocumentsResponse of(List<DocumentDTO> documentDTOList) {
        return new GetAllDocumentsResponse(documentDTOList);
    }
}

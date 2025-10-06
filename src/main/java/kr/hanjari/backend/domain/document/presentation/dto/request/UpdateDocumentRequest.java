package kr.hanjari.backend.domain.document.presentation.dto.request;

import java.util.List;

public record UpdateDocumentRequest(
        String title,
        List<Long> removedFileIdList
) {
}

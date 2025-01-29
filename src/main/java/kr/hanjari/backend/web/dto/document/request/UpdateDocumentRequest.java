package kr.hanjari.backend.web.dto.document.request;

import java.util.List;

public record UpdateDocumentRequest(
        String title,
        List<Long> removedFileIdList
) {
}

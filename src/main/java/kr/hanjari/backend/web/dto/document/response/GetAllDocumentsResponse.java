package kr.hanjari.backend.web.dto.document.response;

import kr.hanjari.backend.web.dto.document.DocumentDTO;

import java.util.List;

public record GetAllDocumentsResponse(
        List<DocumentDTO> documentDTOList
) {
    public static GetAllDocumentsResponse of(List<DocumentDTO> documentDTOList) {
        return new GetAllDocumentsResponse(documentDTOList);
    }
}

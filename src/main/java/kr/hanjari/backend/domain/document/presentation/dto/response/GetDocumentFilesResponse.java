package kr.hanjari.backend.domain.document.presentation.dto.response;

import kr.hanjari.backend.domain.file.domain.dto.FileDTO;

import java.util.List;

public record GetDocumentFilesResponse(
        List<FileDTO> fileDTOList
) {
    public static GetDocumentFilesResponse of(List<FileDTO> fileDTOList) {
        return new GetDocumentFilesResponse(fileDTOList);
    }
}

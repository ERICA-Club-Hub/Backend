package kr.hanjari.backend.web.dto.document.response;

import kr.hanjari.backend.web.dto.file.FileDTO;

import java.util.List;

public record GetDocumentFilesResponse(
        List<FileDTO> fileDTOList
) {
    public static GetDocumentFilesResponse of(List<FileDTO> fileDTOList) {
        return new GetDocumentFilesResponse(fileDTOList);
    }
}

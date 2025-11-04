package kr.hanjari.backend.domain.document.presentation.dto.response;

import kr.hanjari.backend.domain.file.domain.dto.FileDownloadDTO;

import java.util.List;

public record GetDocumentFilesResponse(
        List<FileDownloadDTO> fileDTOList
) {
    public static GetDocumentFilesResponse of(List<FileDownloadDTO> fileDownloadDTOList) {
        return new GetDocumentFilesResponse(fileDownloadDTOList);
    }
}

package kr.hanjari.backend.domain.document.presentation.dto.response;

import kr.hanjari.backend.domain.file.domain.dto.FileDownloadDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "DTO for getting document files response")
public record GetDocumentFilesResponse(
        @Schema(description = "List of file DTOs", nullable = false)
        List<FileDownloadDTO> fileDTOList


) {
    public static GetDocumentFilesResponse of(List<FileDownloadDTO> fileDownloadDTOList) {
        return new GetDocumentFilesResponse(fileDownloadDTOList);
    }
}

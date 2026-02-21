package kr.hanjari.backend.domain.document.application.service;

import kr.hanjari.backend.domain.document.presentation.dto.request.CreateDocumentRequest;
import kr.hanjari.backend.domain.document.presentation.dto.request.UpdateDocumentRequest;
import kr.hanjari.backend.domain.document.presentation.dto.response.GetAllDocumentsResponse;
import kr.hanjari.backend.domain.document.presentation.dto.response.GetDocumentFilesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    GetAllDocumentsResponse getAllDocuments();
    GetDocumentFilesResponse getDocumentFiles(Long documentId);

    Long createDocument(CreateDocumentRequest request, List<MultipartFile> files);
    void deleteDocument(Long documentId);
    void updateDocument(Long documentId, UpdateDocumentRequest request, List<MultipartFile> files);

}

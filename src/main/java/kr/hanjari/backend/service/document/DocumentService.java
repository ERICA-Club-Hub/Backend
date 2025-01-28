package kr.hanjari.backend.service.document;

import kr.hanjari.backend.web.dto.document.request.CreateDocumentRequest;
import kr.hanjari.backend.web.dto.document.request.UpdateDocumentRequest;
import kr.hanjari.backend.web.dto.document.response.GetAllDocumentsResponse;
import kr.hanjari.backend.web.dto.document.response.GetDocumentFilesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    GetAllDocumentsResponse getAllDocuments();
    GetDocumentFilesResponse getDocumentFiles(Long documentId);

    Long createDocument(CreateDocumentRequest request, List<MultipartFile> files);
    void deleteDocument(Long documentId);
    void updateDocument(Long documentId, UpdateDocumentRequest request, List<MultipartFile> files);

}

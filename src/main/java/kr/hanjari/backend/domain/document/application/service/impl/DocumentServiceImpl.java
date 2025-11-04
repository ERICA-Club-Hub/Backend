package kr.hanjari.backend.domain.document.application.service.impl;

import kr.hanjari.backend.domain.document.domain.entity.Document;
import kr.hanjari.backend.domain.file.application.DocumentFileService;
import kr.hanjari.backend.domain.file.application.FileService;
import kr.hanjari.backend.domain.file.domain.dto.FileDownloadDTO;
import kr.hanjari.backend.domain.file.domain.entity.File;
import kr.hanjari.backend.domain.document.domain.entity.DocumentFileId;
import kr.hanjari.backend.domain.document.domain.entity.DocumentFile;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.domain.document.domain.repository.DocumentFileRepository;
import kr.hanjari.backend.domain.document.domain.repository.DocumentRepository;
import kr.hanjari.backend.domain.document.application.service.DocumentService;
import kr.hanjari.backend.infrastructure.s3.S3Service;
import kr.hanjari.backend.domain.document.presentation.dto.DocumentDTO;
import kr.hanjari.backend.domain.document.presentation.dto.request.CreateDocumentRequest;
import kr.hanjari.backend.domain.document.presentation.dto.request.UpdateDocumentRequest;
import kr.hanjari.backend.domain.document.presentation.dto.response.GetAllDocumentsResponse;
import kr.hanjari.backend.domain.document.presentation.dto.response.GetDocumentFilesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final DocumentFileService documentFileService;
    private final FileService fileService;

    public GetAllDocumentsResponse getAllDocuments() {

        return GetAllDocumentsResponse.of(getAllEntity().stream()
                .map(DocumentDTO::from)
                .toList());
    }

    public GetDocumentFilesResponse getDocumentFiles(Long documentId) {

        if (!checkEntityExist(documentId)) {
            throw new GeneralException(ErrorStatus._DATA_NOT_FOUND);
        }

        return GetDocumentFilesResponse.of(documentFileService.getAllFileDownloadDTO(documentId));
    }

    @Transactional
    public Long createDocument(CreateDocumentRequest request,
                               List<MultipartFile> files) {

        if (files.isEmpty()) {
            throw new GeneralException(ErrorStatus._DATA_EMPTY);
        }

        Document newDocument = request.toEntity();
        documentRepository.save(newDocument);

        Long documentId = newDocument.getId();
        files.forEach(file -> {
            Long fileId = fileService.uploadObjectAndSaveFile(file);
            documentFileService.saveNewDocumentFile(documentId, fileId);
        });

        return documentId;
    }

    @Transactional
    public void deleteDocument(Long documentId) {
        if (!checkEntityExist(documentId)) {
            throw new GeneralException(ErrorStatus._DATA_NOT_FOUND);
        }

        List<Long> fileIds = documentFileService.getAllFileIdsByDocumentId(documentId);

        // 매핑 삭제
        documentFileService.deleteAllByDocumentId(documentId);

        // 파일 삭제
        fileIds.forEach(fileService::deleteObjectAndFile);

        // 엔티티 삭제
        documentRepository.deleteById(documentId);
    }

    public void updateDocument(Long documentId, UpdateDocumentRequest request, List<MultipartFile> files) {

        Document document = getEntityById(documentId);

        // 업데이트
        document.updateTitle(request.title());

        // 파일 삭제
        request.removedFileIdList()
                .forEach(fileId -> {
                    documentFileService.deleteByFileId(fileId);
                    fileService.deleteObjectAndFile(fileId);
                });

        // 파일 추가
        files.forEach(file -> {
            Long fileId = fileService.uploadObjectAndSaveFile(file);
            documentFileService.saveNewDocumentFile(documentId, fileId);
        });
    }

    private boolean checkEntityExist(Long documentId) {
        return documentRepository.existsById(documentId);
    }

    private Document getEntityById(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._DATA_NOT_FOUND));
    }

    private List<Document> getAllEntity() {
        return documentRepository.findAll();
    }

}

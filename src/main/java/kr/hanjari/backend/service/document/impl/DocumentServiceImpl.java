package kr.hanjari.backend.service.document.impl;

import jakarta.transaction.Transactional;
import kr.hanjari.backend.domain.Document;
import kr.hanjari.backend.domain.File;
import kr.hanjari.backend.domain.key.DocumentFileId;
import kr.hanjari.backend.domain.mapping.DocumentFile;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.DocumentFileRepository;
import kr.hanjari.backend.repository.DocumentRepository;
import kr.hanjari.backend.service.document.DocumentService;
import kr.hanjari.backend.service.s3.S3Service;
import kr.hanjari.backend.web.dto.document.DocumentDTO;
import kr.hanjari.backend.web.dto.document.request.CreateDocumentRequest;
import kr.hanjari.backend.web.dto.document.request.UpdateDocumentRequest;
import kr.hanjari.backend.web.dto.document.response.GetAllDocumentsResponse;
import kr.hanjari.backend.web.dto.document.response.GetDocumentFilesResponse;
import kr.hanjari.backend.web.dto.file.FileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentFileRepository documentFileRepository;
    private final S3Service s3Service;

    public GetAllDocumentsResponse getAllDocuments() {

        List<Document> documentList = documentRepository.findAll();

        List<DocumentDTO> documentDTOList = documentList.stream()
                .map(DocumentDTO::from)
                .toList();

        return GetAllDocumentsResponse.of(documentDTOList);
    }

    public GetDocumentFilesResponse getDocumentFiles(Long documentId) {

        List<DocumentFile> documentFileList = documentFileRepository.findAllByDocumentId(documentId);

        List<File> fileList = documentFileList.stream()
                .map(DocumentFile::getFile)
                .toList();

        List<FileDTO> fileDTOList = fileList.stream()
                .map(file -> {
                    String fileName = file.getName();
                    String downloadUrl = s3Service.getDownloadUrl(file.getId());
                    return FileDTO.of(fileName, downloadUrl);
                })
                .toList();

        return GetDocumentFilesResponse.of(fileDTOList);
    }

    public Long createDocument(CreateDocumentRequest request,
                               List<MultipartFile> files) {

        Document newDocument = request.toEntity();
        documentRepository.save(newDocument);

        if (!files.isEmpty()) {
            files.forEach(file -> {
                File newFile = s3Service.uploadFile(file);
                DocumentFileId documentFileId = new DocumentFileId();
                DocumentFile documentFile = DocumentFile.builder()
                        .id(documentFileId)
                        .document(newDocument)
                        .file(newFile)
                        .build();
                documentFileRepository.save(documentFile);
            });
        }
        return newDocument.getId();
    }

    public void deleteDocument(Long documentId) {

        List<DocumentFile> documentFiles = documentFileRepository.findAllByDocumentId(documentId);
        documentFileRepository.deleteAll(documentFiles);
        documentRepository.deleteById(documentId);

        documentFiles.stream()
                .map(DocumentFile::getFile)
                .forEach(file -> {
                    s3Service.deleteFile(file.getId());
                });
    }

    public void updateDocument(Long documentId, UpdateDocumentRequest request, List<MultipartFile> files) {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._BAD_REQUEST)); // TODO: 예외 추가

        document.updateTitle(request.title());

        request.removedFileIdList()
                .forEach(fileId -> {
                    documentFileRepository.deleteByFileId(fileId);
                    s3Service.deleteFile(fileId);
                });

        files.forEach(file -> {
            File newFile = s3Service.uploadFile(file);
            DocumentFileId documentFileId = new DocumentFileId();
            DocumentFile documentFile = DocumentFile.builder()
                    .id(documentFileId)
                    .document(document)
                    .file(newFile)
                    .build();
            documentFileRepository.save(documentFile);
        });
    }
}

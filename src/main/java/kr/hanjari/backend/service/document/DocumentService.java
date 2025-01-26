package kr.hanjari.backend.service.document;

import jakarta.transaction.Transactional;
import kr.hanjari.backend.domain.Document;
import kr.hanjari.backend.domain.File;
import kr.hanjari.backend.domain.key.DocumentFileId;
import kr.hanjari.backend.domain.mapping.DocumentFile;
import kr.hanjari.backend.repository.DocumentFileRepository;
import kr.hanjari.backend.repository.DocumentRepository;
import kr.hanjari.backend.service.s3.S3Service;
import kr.hanjari.backend.web.dto.document.DocumentDTO;
import kr.hanjari.backend.web.dto.document.DocumentRequestDTO;
import kr.hanjari.backend.web.dto.document.DocumentResponseDTO;
import kr.hanjari.backend.web.dto.file.FileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentFileRepository documentFileRepository;
    private final S3Service s3Service;

    public Long createDocument(DocumentRequestDTO.CommonDocumentDTO request,
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

    public DocumentResponseDTO.GetAllDocuments getAllDocuments() {

        List<Document> documents = documentRepository.findAll();

        List<DocumentDTO> documentDTOs = documents.stream()
                .map(DocumentDTO::from)
                .toList();

        return DocumentResponseDTO.GetAllDocuments.of(documentDTOs);
    }

    public DocumentResponseDTO.GetDocumentFiles getDocumentFiles(Long documentId) {

        List<DocumentFile> documentFiles = documentFileRepository.findAllByDocumentId(documentId);

        List<File> files = documentFiles.stream()
                .map(DocumentFile::getFile)
                .toList();

        List<FileDTO> fileDTOs = files.stream()
                .map(file -> {
                    String fileName = file.getName();
                    String downloadUrl = s3Service.getDownloadUrl(file.getId());
                    return FileDTO.of(fileName, downloadUrl);
                })
                .toList();

        return DocumentResponseDTO.GetDocumentFiles.of(fileDTOs);
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
}

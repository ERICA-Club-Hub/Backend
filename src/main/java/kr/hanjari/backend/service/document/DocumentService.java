package kr.hanjari.backend.service.document;

import jakarta.transaction.Transactional;
import kr.hanjari.backend.domain.Document;
import kr.hanjari.backend.domain.File;
import kr.hanjari.backend.domain.key.DocumentFileId;
import kr.hanjari.backend.domain.mapping.DocumentFile;
import kr.hanjari.backend.repository.DocumentFileRepository;
import kr.hanjari.backend.repository.DocumentRepository;
import kr.hanjari.backend.service.s3.S3Service;
import kr.hanjari.backend.web.dto.document.DocumentRequestDTO;
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
}

package kr.hanjari.backend.domain.file.application;

import kr.hanjari.backend.domain.document.domain.entity.Document;
import kr.hanjari.backend.domain.document.domain.entity.DocumentFile;
import kr.hanjari.backend.domain.document.domain.entity.DocumentFileId;
import kr.hanjari.backend.domain.document.domain.repository.DocumentFileRepository;
import kr.hanjari.backend.domain.document.domain.repository.DocumentRepository;
import kr.hanjari.backend.domain.file.domain.dto.FileDownloadDTO;
import kr.hanjari.backend.domain.file.domain.entity.File;
import kr.hanjari.backend.domain.file.domain.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentFileService {

    private final DocumentFileRepository documentFileRepository;
    private final DocumentRepository documentRepository;
    private final FileRepository fileRepository;

    private final FileService fileService;

    public void saveNewDocumentFile(Long documentId, Long fileId) {

        Document document = documentRepository.getReferenceById(documentId);
        File file = fileRepository.getReferenceById(fileId);

        DocumentFile documentFile = DocumentFile.builder()
                .id(new DocumentFileId(documentId, fileId))
                .document(document)
                .file(file)
                .build();

        documentFileRepository.save(documentFile);
    }

    public List<FileDownloadDTO> getAllFileDownloadDTO(Long documentId) {
        return getAllFileIdsByDocumentId(documentId).stream()
                .map(fileService::getFileDownloadDTO)
                .toList();
    }

    public List<Long> getAllFileIdsByDocumentId(Long documentId) {
        return documentFileRepository.findAllFileIdsByDocumentId(documentId);
    }

    public void deleteAllByDocumentId(Long documentId) {
        documentFileRepository.deleteAllByDocumentId(documentId);
    }

    public void deleteByFileId(Long fileId) {
        documentFileRepository.deleteByFileId(fileId);
    }
}

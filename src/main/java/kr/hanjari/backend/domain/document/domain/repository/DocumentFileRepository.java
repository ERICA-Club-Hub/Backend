package kr.hanjari.backend.domain.document.domain.repository;

import kr.hanjari.backend.domain.document.domain.entity.DocumentFileId;
import kr.hanjari.backend.domain.document.domain.entity.DocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentFileRepository extends JpaRepository<DocumentFile, DocumentFileId> {
    List<DocumentFile> findAllByDocumentId(Long documentId);
    void deleteByFileId(Long fileId);
}

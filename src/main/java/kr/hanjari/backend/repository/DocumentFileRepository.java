package kr.hanjari.backend.repository;

import kr.hanjari.backend.domain.key.DocumentFileId;
import kr.hanjari.backend.domain.mapping.DocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentFileRepository extends JpaRepository<DocumentFile, DocumentFileId> {
    List<DocumentFile> findAllByDocumentId(Long documentId);
}

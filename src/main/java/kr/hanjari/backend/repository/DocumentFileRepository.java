package kr.hanjari.backend.repository;

import kr.hanjari.backend.domain.key.DocumentFileId;
import kr.hanjari.backend.domain.mapping.DocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentFileRepository extends JpaRepository<DocumentFile, DocumentFileId> {
}

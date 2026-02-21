package kr.hanjari.backend.domain.document.domain.repository;

import kr.hanjari.backend.domain.document.domain.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}

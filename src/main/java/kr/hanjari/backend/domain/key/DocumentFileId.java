package kr.hanjari.backend.domain.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class DocumentFileId implements Serializable {

    @Column(name = "document_id", nullable = false)
    private Long documentId;

    @Column(name = "file_id", nullable = false)
    private Long fileId;
}

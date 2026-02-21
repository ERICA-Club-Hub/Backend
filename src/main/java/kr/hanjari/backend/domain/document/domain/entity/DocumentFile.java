package kr.hanjari.backend.domain.document.domain.entity;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.file.domain.entity.File;
import lombok.*;

@Entity
@Table(name = "document_file")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DocumentFile {

    @EmbeddedId
    private DocumentFileId id;

    @MapsId("documentId")
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @MapsId("fileId")
    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

}

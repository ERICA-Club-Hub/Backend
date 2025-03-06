package kr.hanjari.backend.domain.mapping;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.Document;
import kr.hanjari.backend.domain.File;
import kr.hanjari.backend.domain.key.DocumentFileId;
import lombok.*;

import java.io.Serializable;

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

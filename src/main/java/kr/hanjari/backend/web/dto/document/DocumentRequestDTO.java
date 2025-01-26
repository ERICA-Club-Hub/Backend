package kr.hanjari.backend.web.dto.document;

import kr.hanjari.backend.domain.Document;
import lombok.Getter;

import java.util.List;

public class DocumentRequestDTO {

    @Getter
    public static class CommonDocumentDTO {
        private String title;

        public Document toEntity() {
            return Document.builder()
                    .title(title)
                    .build();
        }
    }

    @Getter
    public static class UpdateDocumentDTO {
        private String title;
        private List<Long> removedFileList;
    }

}

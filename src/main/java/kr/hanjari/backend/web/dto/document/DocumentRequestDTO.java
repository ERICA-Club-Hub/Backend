package kr.hanjari.backend.web.dto.document;

import kr.hanjari.backend.domain.Document;
import lombok.Getter;

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

}

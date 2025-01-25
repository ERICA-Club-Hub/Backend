package kr.hanjari.backend.web.dto.document;

import kr.hanjari.backend.domain.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DocumentRequestDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CommonDocumentDTO {
        private String title;

        public Document toEntity() {
            return Document.builder()
                    .title(title)
                    .build();
        }
    }

}

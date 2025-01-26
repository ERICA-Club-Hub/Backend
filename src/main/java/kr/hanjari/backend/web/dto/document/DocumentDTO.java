package kr.hanjari.backend.web.dto.document;

import kr.hanjari.backend.domain.Document;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class DocumentDTO {
    private final Long id;
    private final String title;
    private final LocalDate date;

    public static DocumentDTO from(Document document) {
        return DocumentDTO.builder()
                .id(document.getId())
                .title(document.getTitle())
                .date(document.getCreatedAt().toLocalDate())
                .build();
    }
}

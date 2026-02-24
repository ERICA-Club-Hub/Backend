package kr.hanjari.backend.domain.faq.presentation.dto.request;

import kr.hanjari.backend.domain.faq.domain.entity.Faq;
import kr.hanjari.backend.domain.faq.domain.enums.FaqCategory;

public record FaqRequest(
        String title,
        String content,
        FaqCategory category
) {
    public Faq toEntity() {
        return Faq.builder()
                .title(title)
                .content(content)
                .category(category)
                .build();
    }

}

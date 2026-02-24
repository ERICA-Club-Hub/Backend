package kr.hanjari.backend.domain.faq.presentation.dto;

import kr.hanjari.backend.domain.faq.domain.entity.Faq;
import kr.hanjari.backend.domain.faq.domain.enums.FaqCategory;

public record FaqDTO(
        Long faqId,
        String title,
        String content,
        FaqCategory category
) {
    public static FaqDTO from(Faq faq) {
        return new FaqDTO(
                faq.getId(),
                faq.getTitle(),
                faq.getContent(),
                faq.getCategory()
        );
    }
}

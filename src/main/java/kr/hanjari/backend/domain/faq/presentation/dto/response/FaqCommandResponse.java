package kr.hanjari.backend.domain.faq.presentation.dto.response;

public record FaqCommandResponse(
        Long faqId
) {
    public static FaqCommandResponse of(Long faqId) {
        return new FaqCommandResponse(faqId);
    }
}

package kr.hanjari.backend.domain.faq.presentation.dto.response;

import kr.hanjari.backend.domain.faq.presentation.dto.FaqDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetAllFaqResponse(
        Long totalElements,
        List<FaqDTO> faqDTOList
) {
    public static GetAllFaqResponse from(Page<FaqDTO> faqDTOPage) {

        return new GetAllFaqResponse(
                faqDTOPage.getTotalElements(),
                faqDTOPage.getContent()
        );
    }
}

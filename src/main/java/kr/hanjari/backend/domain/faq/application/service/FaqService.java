package kr.hanjari.backend.domain.faq.application.service;

import kr.hanjari.backend.domain.faq.presentation.dto.FaqDTO;
import kr.hanjari.backend.domain.faq.presentation.dto.request.FaqRequest;
import kr.hanjari.backend.domain.faq.presentation.dto.response.FaqCommandResponse;
import kr.hanjari.backend.domain.faq.presentation.dto.response.GetAllFaqResponse;

public interface FaqService {

    FaqCommandResponse createFaq(FaqRequest faqRequest);

    GetAllFaqResponse getAllFaq(int page, int size);
    FaqDTO getFaq(long faqId);

    void deleteFaq(long faqId);
}

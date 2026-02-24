package kr.hanjari.backend.domain.faq.application.service;

import kr.hanjari.backend.domain.faq.presentation.dto.request.FaqRequest;
import kr.hanjari.backend.domain.faq.presentation.dto.response.FaqCommandResponse;

public interface FaqService {

    FaqCommandResponse createFaq(FaqRequest faqRequest);
}

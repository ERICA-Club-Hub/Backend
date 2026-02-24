package kr.hanjari.backend.domain.faq.application.service.impl;

import kr.hanjari.backend.domain.faq.application.service.FaqService;
import kr.hanjari.backend.domain.faq.domain.entity.Faq;
import kr.hanjari.backend.domain.faq.domain.repository.FaqRepository;
import kr.hanjari.backend.domain.faq.presentation.dto.request.FaqRequest;
import kr.hanjari.backend.domain.faq.presentation.dto.response.FaqCommandResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FaqServiceImpl implements FaqService {

    private final FaqRepository faqRepository;

    @Override
    public FaqCommandResponse createFaq(FaqRequest request) {

        Faq newFaq = request.toEntity();
        faqRepository.save(newFaq);

        return new FaqCommandResponse(newFaq.getId());
    }

}

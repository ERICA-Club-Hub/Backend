package kr.hanjari.backend.domain.faq.application.service.impl;

import kr.hanjari.backend.domain.faq.application.service.FaqService;
import kr.hanjari.backend.domain.faq.domain.entity.Faq;
import kr.hanjari.backend.domain.faq.domain.repository.FaqRepository;
import kr.hanjari.backend.domain.faq.presentation.dto.FaqDTO;
import kr.hanjari.backend.domain.faq.presentation.dto.request.FaqRequest;
import kr.hanjari.backend.domain.faq.presentation.dto.response.FaqCommandResponse;
import kr.hanjari.backend.domain.faq.presentation.dto.response.GetAllFaqResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public GetAllFaqResponse getAllFaq(int page, int size) {

        Page<Faq> faqPage = faqRepository.findAll(PageRequest.of(page, size));
        Page<FaqDTO> faqDTOPage = faqPage.map(FaqDTO::from);

        return GetAllFaqResponse.from(faqDTOPage);
    }

}

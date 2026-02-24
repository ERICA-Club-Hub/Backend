package kr.hanjari.backend.domain.faq.application.service.impl;

import kr.hanjari.backend.domain.faq.application.service.FaqService;
import kr.hanjari.backend.domain.faq.domain.entity.Faq;
import kr.hanjari.backend.domain.faq.domain.repository.FaqRepository;
import kr.hanjari.backend.domain.faq.presentation.dto.FaqDTO;
import kr.hanjari.backend.domain.faq.presentation.dto.request.FaqRequest;
import kr.hanjari.backend.domain.faq.presentation.dto.response.FaqCommandResponse;
import kr.hanjari.backend.domain.faq.presentation.dto.response.GetAllFaqResponse;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
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
    @Transactional
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

    @Override
    public FaqDTO getFaq(long faqId) {

        Faq faq = getEntityById(faqId);

        return FaqDTO.from(faq);
    }

    @Override
    @Transactional
    public void deleteFaq(long faqId) {

        faqRepository.deleteById(faqId);
    }

    private Faq getEntityById(long faqId) {

        return faqRepository.findById(faqId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._FAQ_NOT_FOUND));
    }

}

package kr.hanjari.backend.domain.feedback.application.service.impl;

import jakarta.transaction.Transactional;
import kr.hanjari.backend.domain.feedback.domain.entity.Feedback;
import kr.hanjari.backend.domain.feedback.domain.repository.FeedbackRepository;
import kr.hanjari.backend.domain.feedback.application.service.FeedbackService;
import kr.hanjari.backend.domain.feedback.presentation.dto.FeedbackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public void saveFeedback(FeedbackDTO feedbackDTO) {

        Feedback newFeedback = feedbackDTO.toFeedback();
        feedbackRepository.save(newFeedback);
    }
}

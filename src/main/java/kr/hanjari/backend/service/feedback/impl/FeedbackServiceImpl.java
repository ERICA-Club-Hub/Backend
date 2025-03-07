package kr.hanjari.backend.service.feedback.impl;

import jakarta.transaction.Transactional;
import kr.hanjari.backend.domain.Feedback;
import kr.hanjari.backend.repository.FeedbackRepository;
import kr.hanjari.backend.service.feedback.FeedbackService;
import kr.hanjari.backend.web.dto.feedback.FeedbackDTO;
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

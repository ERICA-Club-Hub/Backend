package kr.hanjari.backend.domain.feedback.application.service.impl;

import kr.hanjari.backend.domain.feedback.domain.entity.Feedback;
import kr.hanjari.backend.domain.feedback.domain.repository.FeedbackRepository;
import kr.hanjari.backend.domain.feedback.application.service.FeedbackService;
import kr.hanjari.backend.domain.feedback.presentation.dto.request.FeedbackRequest;
import kr.hanjari.backend.domain.feedback.presentation.dto.response.FeedbackCommandResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    @Transactional
    public FeedbackCommandResponse createFeedback(FeedbackRequest request) {

        Feedback newFeedback = request.toEntity();

        return FeedbackCommandResponse.of(save(newFeedback).getId());
    }

    private Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }
}

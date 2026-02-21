package kr.hanjari.backend.domain.feedback.application.service.impl;

import kr.hanjari.backend.domain.feedback.domain.entity.Feedback;
import kr.hanjari.backend.domain.feedback.domain.repository.FeedbackRepository;
import kr.hanjari.backend.domain.feedback.application.service.FeedbackService;
import kr.hanjari.backend.domain.feedback.presentation.dto.FeedbackDTO;
import kr.hanjari.backend.domain.feedback.presentation.dto.request.FeedbackRequest;
import kr.hanjari.backend.domain.feedback.presentation.dto.response.FeedbackCommandResponse;
import kr.hanjari.backend.domain.feedback.presentation.dto.response.GetFeedbacksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public GetFeedbacksResponse getFeedbacks(int page, int size) {

        Page<Feedback> feedbacks = feedbackRepository.findAll(PageRequest.of(page, size));
        Page<FeedbackDTO> feedbackDTOPage = feedbacks.map(FeedbackDTO::fromFeedback);

        return GetFeedbacksResponse.of(feedbackDTOPage);
    }

    private Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }
}

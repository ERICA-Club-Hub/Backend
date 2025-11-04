package kr.hanjari.backend.domain.feedback.application.service;

import kr.hanjari.backend.domain.feedback.presentation.dto.request.FeedbackRequest;
import kr.hanjari.backend.domain.feedback.presentation.dto.response.FeedbackCommandResponse;

public interface FeedbackService {

    FeedbackCommandResponse createFeedback(FeedbackRequest request);

}

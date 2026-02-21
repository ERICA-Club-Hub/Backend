package kr.hanjari.backend.domain.feedback.application.service;

import kr.hanjari.backend.domain.feedback.presentation.dto.request.FeedbackRequest;
import kr.hanjari.backend.domain.feedback.presentation.dto.response.FeedbackCommandResponse;
import kr.hanjari.backend.domain.feedback.presentation.dto.response.GetFeedbacksResponse;

public interface FeedbackService {

    FeedbackCommandResponse createFeedback(FeedbackRequest request);

    GetFeedbacksResponse getFeedbacks(int page, int size);

}

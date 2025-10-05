package kr.hanjari.backend.domain.feedback.application.service;

import kr.hanjari.backend.domain.feedback.presentation.dto.FeedbackDTO;

public interface FeedbackService {

    void saveFeedback(FeedbackDTO feedbackDTO);

}

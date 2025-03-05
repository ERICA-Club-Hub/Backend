package kr.hanjari.backend.service.feedback;

import kr.hanjari.backend.web.dto.feedback.FeedbackDTO;

public interface FeedbackService {

    void saveFeedback(FeedbackDTO feedbackDTO);

}

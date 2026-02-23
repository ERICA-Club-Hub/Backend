package kr.hanjari.backend.domain.feedback.presentation.dto.response;

import kr.hanjari.backend.domain.feedback.domain.entity.Feedback;
import kr.hanjari.backend.domain.feedback.presentation.dto.FeedbackDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetFeedbacksResponse(
        long totalElements,
        List<FeedbackDTO> feedbackDTOList
) {
    public static GetFeedbacksResponse of(Page<FeedbackDTO> feedbackPage) {
        return new GetFeedbacksResponse(feedbackPage.getTotalElements(), feedbackPage.getContent());
    }
}

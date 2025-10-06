package kr.hanjari.backend.domain.feedback.presentation.dto;

import kr.hanjari.backend.domain.feedback.domain.entity.Feedback;

public record FeedbackDTO(
        String content
) {
    public Feedback toFeedback() {
        return Feedback.builder()
                .content(content)
                .build();
    }
}

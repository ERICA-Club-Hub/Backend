package kr.hanjari.backend.domain.feedback.presentation.dto.request;

import kr.hanjari.backend.domain.feedback.domain.entity.Feedback;

public record FeedbackRequest(
        String content
) {
    public Feedback toEntity() {
        return Feedback.builder()
                .content(content)
                .build();
    }
}

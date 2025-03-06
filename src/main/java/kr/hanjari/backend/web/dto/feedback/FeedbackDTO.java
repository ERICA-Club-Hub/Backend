package kr.hanjari.backend.web.dto.feedback;

import kr.hanjari.backend.domain.Feedback;

public record FeedbackDTO(
        String content
) {
    public Feedback toFeedback() {
        return Feedback.builder()
                .content(content)
                .build();
    }
}

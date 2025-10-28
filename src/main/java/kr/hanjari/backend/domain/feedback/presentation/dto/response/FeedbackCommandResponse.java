package kr.hanjari.backend.domain.feedback.presentation.dto.response;

public record FeedbackCommandResponse(
        Long feedbackId
) {
    public static FeedbackCommandResponse of(Long feedbackId) {
        return new FeedbackCommandResponse(feedbackId);
    }
}

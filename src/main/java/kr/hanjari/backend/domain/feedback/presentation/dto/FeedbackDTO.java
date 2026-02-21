package kr.hanjari.backend.domain.feedback.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import kr.hanjari.backend.domain.feedback.domain.entity.Feedback;

import java.time.LocalDateTime;

@Schema(description = "DTO for feedback")
public record FeedbackDTO(
        Long feedbackId,
        @Schema(description = "Feedback content", example = "Great service!", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Feedback content cannot be empty.")
        String content,
        LocalDateTime dateTime
) {
    public Feedback toFeedback() {
        return Feedback.builder()
                .content(content)
                .build();
    }

    public static FeedbackDTO fromFeedback(Feedback feedback) {
        return new FeedbackDTO(
                feedback.getId(),
                feedback.getContent(),
                feedback.getCreatedAt());
    }
}

package kr.hanjari.backend.domain.activity.presentation.dto.response;

public record ActivityCommandResponse(
        Long activityId
) {
    public static ActivityCommandResponse of(Long activityId) {
        return new ActivityCommandResponse(activityId);
    }
}

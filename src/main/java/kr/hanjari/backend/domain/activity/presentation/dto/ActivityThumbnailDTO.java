package kr.hanjari.backend.domain.activity.presentation.dto;

public record ActivityThumbnailDTO(
        Long activityId,
        String thumbnailUrl
) {
    public static ActivityThumbnailDTO of(Long activityId, String thumbnailUrl) {
        return new ActivityThumbnailDTO(activityId, thumbnailUrl);
    }
}

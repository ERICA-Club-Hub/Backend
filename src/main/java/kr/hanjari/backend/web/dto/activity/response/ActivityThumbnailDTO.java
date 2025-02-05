package kr.hanjari.backend.web.dto.activity.response;

public record ActivityThumbnailDTO(
        Long activityId,
        String thumbnailUrl
) {
    public static ActivityThumbnailDTO of(Long activityId, String thumbnailUrl) {
        return new ActivityThumbnailDTO(activityId, thumbnailUrl);
    }
}

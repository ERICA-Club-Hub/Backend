package kr.hanjari.backend.domain.activity.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for activity thumbnail")
public record ActivityThumbnailDTO(
        @Schema(description = "Activity ID", nullable = false, example = "1")
        Long activityId,
        @Schema(description = "Thumbnail image URL", nullable = false, example = "https://.../thumbnail.png")
        String thumbnailUrl
) {
    public static ActivityThumbnailDTO of(Long activityId, String thumbnailUrl) {
        return new ActivityThumbnailDTO(activityId, thumbnailUrl);
    }
}

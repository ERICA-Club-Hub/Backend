package kr.hanjari.backend.domain.activity.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for activity image")
public record ActivityImageDTO(
        @Schema(description = "Image order index", nullable = false, example = "1")
        Integer orderIndex,
        @Schema(description = "Image URL", nullable = false, example = "https://.../image.png")
        String imageUrl
) {
    public static ActivityImageDTO of(Integer orderIndex, String imageUrl) {
        return new ActivityImageDTO(orderIndex, imageUrl);
    }
}

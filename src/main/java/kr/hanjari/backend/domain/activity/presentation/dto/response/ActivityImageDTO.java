package kr.hanjari.backend.domain.activity.presentation.dto.response;

public record ActivityImageDTO(
        Integer orderIndex,
        String imageUrl
) {
    public static ActivityImageDTO of(Integer orderIndex, String imageUrl) {
        return new ActivityImageDTO(orderIndex, imageUrl);
    }
}

package kr.hanjari.backend.web.dto.activity.response;

public record ActivityImageDTO(
        Integer orderIndex,
        String imageUrl
) {
    public static ActivityImageDTO of(Integer orderIndex, String imageUrl) {
        return new ActivityImageDTO(orderIndex, imageUrl);
    }
}

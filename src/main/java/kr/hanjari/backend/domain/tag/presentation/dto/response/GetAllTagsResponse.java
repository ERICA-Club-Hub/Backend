package kr.hanjari.backend.domain.tag.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import kr.hanjari.backend.domain.tag.domain.entity.Tag;

@Schema(description = "전체 태그 목록 응답")
public record GetAllTagsResponse(
        @Schema(description = "태그 목록 (우선순위 순)")
        List<TagResponse> tags
) {
    public static GetAllTagsResponse of(List<Tag> tags) {
        return new GetAllTagsResponse(
                tags.stream().map(TagResponse::from).toList()
        );
    }

    @Schema(description = "태그")
    public record TagResponse(
            @Schema(description = "태그 ID", example = "1")
            Long id,
            @Schema(description = "태그명", example = "저비용")
            String name
    ) {
        public static TagResponse from(Tag tag) {
            return new TagResponse(tag.getId(), tag.getName());
        }
    }
}

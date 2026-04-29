package kr.hanjari.backend.domain.tag.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "생성된 태그 ID 응답")
public record TagIdResponse(
        @Schema(description = "태그 ID", example = "1")
        Long tagId
) {
    public static TagIdResponse of(Long tagId) {
        return new TagIdResponse(tagId);
    }
}
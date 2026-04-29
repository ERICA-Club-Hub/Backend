package kr.hanjari.backend.domain.tag.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "태그 생성 요청")
public record CreateTagRequest(
        @Schema(description = "태그명", example = "저비용")
        @NotBlank
        String name,

        @Schema(description = "우선순위 (낮을수록 먼저 표시)", example = "1")
        @NotNull
        Integer priority
) {
}
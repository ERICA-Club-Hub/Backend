package kr.hanjari.backend.domain.tag.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hanjari.backend.domain.tag.application.command.TagCommandService;
import kr.hanjari.backend.domain.tag.application.query.TagQueryService;
import kr.hanjari.backend.domain.tag.presentation.dto.request.CreateTagRequest;
import kr.hanjari.backend.domain.tag.presentation.dto.response.GetAllTagsResponse;
import kr.hanjari.backend.domain.tag.presentation.dto.response.TagIdResponse;
import kr.hanjari.backend.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Tag(name = "Tag", description = "Tag API")
public class TagController {

    private final TagCommandService tagCommandService;
    private final TagQueryService tagQueryService;

    @Operation(summary = "[태그] 전체 태그 목록 조회", description = """
            ## 동아리에 붙일 수 있는 전체 태그 목록을 우선순위 순으로 조회합니다.
            """)
    @GetMapping
    public ApiResponse<GetAllTagsResponse> getAllTags() {
        return ApiResponse.onSuccess(tagQueryService.getAllTags());
    }

    @Operation(summary = "[태그] 태그 생성", description = """
            ## 태그를 생성합니다.
            ### Request Body
            - **name**: 태그명 (예: 저비용, 대회출전, MT, 정기모임, 수시모임, 스터디, 상시모집)
            - **priority**: 우선순위 (낮을수록 우선 표시)
            """)
    @PostMapping
    public ApiResponse<TagIdResponse> createTag(@RequestBody @Valid CreateTagRequest request) {
        Long tagId = tagCommandService.createTag(request.name(), request.priority());
        return ApiResponse.onSuccess(TagIdResponse.of(tagId));
    }
}
package kr.hanjari.backend.domain.tag.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.domain.tag.application.command.TagCommandService;
import kr.hanjari.backend.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
@Tag(name = "Tag", description = "Tag API")
public class ClubTagController {

    private final TagCommandService tagCommandService;

    @Operation(summary = "[태그] 동아리 태그 추가", description = """
            ## 동아리에 태그를 추가합니다.
            ### Path Variables
            - **clubId**: 태그를 추가할 동아리 ID
            - **tagId**: 추가할 태그 ID
            """)
    @PostMapping("/{clubId}/tags/{tagId}")
    public ApiResponse<Void> addTagToClub(@PathVariable Long clubId, @PathVariable Long tagId) {
        tagCommandService.addTagToClub(clubId, tagId);
        return ApiResponse.onSuccess();
    }

    @Operation(summary = "[태그] 동아리 태그 삭제", description = """
            ## 동아리에서 태그를 삭제합니다.
            ### Path Variables
            - **clubId**: 태그를 삭제할 동아리 ID
            - **tagId**: 삭제할 태그 ID
            """)
    @DeleteMapping("/{clubId}/tags/{tagId}")
    public ApiResponse<Void> removeTagFromClub(@PathVariable Long clubId, @PathVariable Long tagId) {
        tagCommandService.removeTagFromClub(clubId, tagId);
        return ApiResponse.onSuccess();
    }
}

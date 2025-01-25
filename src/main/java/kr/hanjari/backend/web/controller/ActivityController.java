package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.web.dto.activity.ActivityRequestDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/activities")
public class ActivityController {

    @Tag(name = "활동로그", description = "활동로그 관련 API")
    @Operation(summary = "[활동로그] 활동로그 게시", description = """
            ## 활동로그를 게시합니다.
            ### PathVariable
            - **clubId**: 활동로그를 게시하려는 동아리의 id
            ### RequestBody
            - **date**: 날짜
            - **title**: 제목
            ### Multipart/form-data
            - **images**: 업로드하려는 이미지 리스트
            """)
    @PostMapping("/{clubId}")
    public void postNewActivity(@PathVariable Long clubId,
                                @RequestPart ActivityRequestDTO.CommonActivityDTO request,
                                @RequestPart List<MultipartFile> images) {
        return;
    }

    @Tag(name = "활동로그", description = "활동로그 관련 API")
    @Operation(summary = "[활동로그] 활동로그 수정", description = """
            ## 활동로그를 수정합니다.
            ### PathVariable
            - **activityId**: 수정을 희망하는 활동로그의 id
            ### RequestBody
            - **date**: 날짜
            - **title**: 제목
            ### Multipart/form-data
            - **images**: 업로드하려는 이미지 리스트
            """)
    @PatchMapping("/{activityId}")
    public void updateActivity(@PathVariable Long activityId,
                               @RequestPart ActivityRequestDTO.CommonActivityDTO request,
                               @RequestPart List<MultipartFile> images) {
        return;
    }

    @Tag(name = "활동로그", description = "활동로그 관련 API")
    @Operation(summary = "[활동로그] 활동로그 조회", description = """
            ## 동아리의 활동로그를 모두 조회합니다.
            ### PathVariable
            - **clubId**: 활동로그 조회를 희망한는 동아리의 id
            """)
    @GetMapping("/{clubId}")
    public void getAllActivity(@PathVariable Long clubId) {
        return;
    }

    @Tag(name = "활동로그", description = "활동로그 관련 API")
    @Operation(summary = "[활동로그] 활동로그 상세 조회", description = """
            ## 활동로그 하나를 상세조회합니다.
            ### PathVariable
            - **activityId**: 조회를 희망하는 활동로그의 id
            """)
    @GetMapping("/{activityId}")
    public void getActivityDetail(@PathVariable Long activityId) {
        return;
    }

    @Tag(name = "활동로그", description = "활동로그 관련 API")
    @Operation(summary = "[활동로그] 활동로그 삭제", description = """
            ## 활동로그 하나를 삭제합니다.
            ### PathVariable
            - **activityId**: 삭제를 희망하는 활동로그의 id
            """)
    @DeleteMapping("/{activityId}")
    public void deleteActivity(@PathVariable Long activityId) {
        return;
    }

}

package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.service.activity.ActivityService;
import kr.hanjari.backend.web.dto.activity.ActivityRequestDTO;
import kr.hanjari.backend.web.dto.activity.request.CreateActivityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/activities")
@RequiredArgsConstructor
@Tag(name = "활동로그", description = "활동로그 관련 API")
public class ActivityController {

    private final ActivityService activityService;

    @Operation(summary = "[활동로그] 활동로그 게시", description = """
        ## 📤 동아리 활동로그를 게시합니다.

        ### 🔹 PathVariable
        #### 📌 clubId: club의 ID
        
        ### 🔹 Request
        #### 📌 requestBody (JSON)
        - **title**: 제목
        - **date**: 날짜

        #### 📌 files (multipart/form-data 리스트)
        - **업로드할 파일 리스트**

        ### 🔹 Response
        - **생성된 activity의 ID**
        """)
    @PostMapping("/{clubId}")
    public ApiResponse<Long> postNewActivity(@PathVariable Long clubId, // TODO: token으로 치환
                                             @RequestPart CreateActivityRequest requestBody,
                                             @RequestPart List<MultipartFile> images) {

        Long result = activityService.createActivity(clubId, requestBody, images);
        return ApiResponse.onSuccess(result);
    }

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

    @Operation(summary = "[활동로그] 활동로그 조회", description = """
            ## 동아리의 활동로그를 모두 조회합니다.
            ### PathVariable
            - **clubId**: 활동로그 조회를 희망한는 동아리의 id
            """)
    @GetMapping("/{clubId}")
    public void getAllActivity(@PathVariable Long clubId) {
        return;
    }

    @Operation(summary = "[활동로그] 활동로그 상세 조회", description = """
            ## 활동로그 하나를 상세조회합니다.
            ### PathVariable
            - **activityId**: 조회를 희망하는 활동로그의 id
            """)
    @GetMapping("/{activityId}")
    public void getActivityDetail(@PathVariable Long activityId) {
        return;
    }

    @Operation(summary = "[활동로그] 활동로그 삭제", description = """
            ## 🗑 활동로그를 삭제합니다.

            ### 🔹 PathVariable
            #### 📌 activityId: 삭제할 activity의 ID
            """)
    @DeleteMapping("/{activityId}")
    public ApiResponse<Void> deleteActivity(@PathVariable Long activityId) {

        activityService.deleteActivity(activityId);
        return ApiResponse.onSuccess();
    }

}

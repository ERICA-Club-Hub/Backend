package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.service.activity.ActivityService;
import kr.hanjari.backend.web.dto.activity.ActivityRequestDTO;
import kr.hanjari.backend.web.dto.activity.request.CreateActivityRequest;
import kr.hanjari.backend.web.dto.activity.request.UpdateActivityRequest;
import kr.hanjari.backend.web.dto.activity.response.GetAllActivityResponse;
import kr.hanjari.backend.web.dto.activity.response.GetSpecificActivityResponse;
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
        ## ✏️ 활동로그를 수정합니다.
        
        ### 🔹 PathVariable
        #### 📌 activityId: 수정할 activity의 ID

        ### 🔹 Request
        #### 📌 requestBody (JSON)
        - **content**: 수정할 내용
        - **date**: 수정할 날짜
        - **changedImageIDList**: 삭제할 파일 ID 리스트

        #### 📌 files (multipart/form-data 리스트)
        - **새롭게 추가할 파일 리스트(필수 x, 있는 경우에만 입력)**
        """)
    @PatchMapping("/{activityId}")
    public ApiResponse<Void> updateActivity(@PathVariable Long activityId,   // TODO: token 검증
                               @RequestPart UpdateActivityRequest requestBody,
                               @RequestPart List<MultipartFile> images) {

        activityService.updateActivity(activityId, requestBody, images);
        return ApiResponse.onSuccess();
    }

    @Operation(summary = "[활동로그] 활동로그 조회", description = """
        ## 📄 동아리의 활동로그를 모두 조회합니다.
        
        ### 🔹 PathVariable
        #### 📌 clubId: 활동로그를 조회할 club의 ID
        
        ### 🔹 Response
        - **thumbnailUrlList**: 각 활동로그 대표 사진의 url 리스트
        """)
    @GetMapping("/{clubId}")
    public ApiResponse<GetAllActivityResponse> getAllActivity(@PathVariable Long clubId) {

        GetAllActivityResponse result = activityService.getAllActivity(clubId);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "[활동로그] 활동로그 상세 조회", description = """
        ## 📄 활동로그 하나를 상세조회합니다.
        
        ### 🔹 PathVariable
        #### 📌 activityId: 상세조회할 activity의 ID
        """)
    @GetMapping("/{activityId}")
    public ApiResponse<GetSpecificActivityResponse> getSpecificActivity(@PathVariable Long activityId) {

        GetSpecificActivityResponse result = activityService.getSpecificActivity(activityId);
        return ApiResponse.onSuccess(result);
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

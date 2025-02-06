package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.service.announcement.AnnouncementService;
import kr.hanjari.backend.web.dto.announcement.request.CommonAnnouncementRequest;
import kr.hanjari.backend.web.dto.announcement.response.GetAllAnnouncementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
@Tag(name = "📢 총동연 공지사항", description = "총동연 공지사항 관련 API")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Operation(summary = "[총동연 공지사항] 총동연 공지사항 생성", description = """
        ## 📤 총동연 공지사항을 생성합니다.

        ### 🔹 Request
        #### 📌 requestBody (JSON)
        - **title**: 공지사항 제목
        - **url**: 링크 URL

        #### 📌 thumbnail (multipart/form-data)
        - **썸네일 이미지 파일**

        ### 🔹 Response
        - **생성된 공지사항의 ID**
        """
    )
    @PostMapping(value = "/", consumes = {"application/json", "multipart/form-data"})
    public ApiResponse<Long> postNewAnnouncement(@RequestPart(name = "requestBody") CommonAnnouncementRequest requestBody,
                                                 @RequestPart(name = "thumbnail") MultipartFile thumbnail) {

        Long result = announcementService.createAnnouncement(requestBody, thumbnail);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "[총동연 공지사항] 총동연 공지사항 전체 조회", description = """
        ## 📄 총동연 공지사항을 전부 조회합니다.

        ### 🔹 Response
        #### 📌 AnnouncementDTOList
        - **AnnouncementDTO** 객체들의 리스트

        #### 📌 AnnouncementDTO
        - **announcementId**: 공지사항 ID
        - **title**: 공지사항 제목
        - **date**: 공지 날짜
        - **url**: 관련 링크
        - **thumbnail**: 썸네일 이미지 링크
        """
    )
    @GetMapping("/")
    public ApiResponse<GetAllAnnouncementResponse> getAllAnnouncement() {

        GetAllAnnouncementResponse result = announcementService.getAllAnnouncement();
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "[총동연 공지사항] 총동연 공지사항 수정", description = """
        ## ✏️ 총동연 공지사항을 수정합니다.

        ### 🔹 PathVariable
        #### 📌 announcementId: 수정할 공지사항의 ID

        ### 🔹 Request
        #### 📌 requestBody (JSON)
        - **title**: 공지사항 제목
        - **url**: 링크 URL

        #### 📌 thumbnail (multipart/form-data)
        - **썸네일 이미지 (필수 X, 이미지도 수정하는 경우에만 입력)**
        """
    )
    @PatchMapping(value = "/{announcementId}", consumes = {"application/json", "multipart/form-data"})
    public ApiResponse<Void> updateAnnouncement(@PathVariable Long announcementId,
                                                @RequestPart(name = "requestBody") CommonAnnouncementRequest requestBody,
                                                @RequestPart(name = "thumbnail", required = false) MultipartFile thumbnail) {

        announcementService.updateAnnouncement(announcementId, requestBody, thumbnail);
        return ApiResponse.onSuccess();
    }

    @Operation(summary = "[총동연 공지사항] 총동연 공지사항 삭제", description = """
        ## 🗑 총동연 공지사항을 삭제합니다.

        ### 🔹 PathVariable
        #### 📌 announcementId: 삭제할 공지사항의 ID
        """
    )
    @DeleteMapping("/{announcementId}")
    public ApiResponse<Void> deleteAnnouncement(@PathVariable Long announcementId) {

        announcementService.deleteAnnouncement(announcementId);
        return ApiResponse.onSuccess();
    }
}

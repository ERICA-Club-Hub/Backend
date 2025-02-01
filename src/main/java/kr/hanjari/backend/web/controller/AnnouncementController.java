package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.service.announcement.AnnouncementService;
import kr.hanjari.backend.web.dto.announcement.CommonAnnouncement;
import kr.hanjari.backend.web.dto.announcement.request.CommonAnnouncementRequest;
import kr.hanjari.backend.web.dto.announcement.response.GetAllAnnouncementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Tag(name = "총동연 공지사항", description = "총동연 공지사항 관련 API")
    @Operation(summary = "[총동연 공지사항] 총동연 공지사항 생성", description = """
            총동연 공지사항을 생성합니다.
            ### RequestBody
            - **title**: 공지사항 제목
            - **url**: 링크 url
            ### RequestPart
            - **thumbnail**: 썸네일 이미지
            """)
    @PostMapping(value = "/", consumes = {"application/json", "multipart/form-data"})
    public ApiResponse<Long> postNewAnnouncement(@RequestPart(name = "body") CommonAnnouncementRequest requestBody,
                                                 @RequestPart(name = "thumbnail") MultipartFile thumbnail) {

        Long result = announcementService.createAnnouncement(requestBody, thumbnail);
        return ApiResponse.onSuccess(result);
    }

    @Tag(name = "총동연 공지사항", description = "총동연 공지사항 관련 API")
    @Operation(summary = "[총동연 공지사항] 총동연 공지사항 전체 조회", description = """
            총동연 공지사항을 전부 조회합니다.
            """)
    @GetMapping("/")
    public ApiResponse<GetAllAnnouncementResponse> getAllAnnouncement() {

        GetAllAnnouncementResponse result = announcementService.getAllAnnouncement();
        return ApiResponse.onSuccess(result);
    }

    @Tag(name = "총동연 공지사항", description = "총동연 공지사항 관련 API")
    @Operation(summary = "[총동연 공지사항] 총동연 공지사항 수정", description = """
            총동연 공지사항을 수정합니다.
            ### PathVariable
            - **id**: 수정을 희망하는 공지사항의 id
            ### RequestBody
            - **title**: 공지사항 제목
            - **url**: 링크 url
            ### RequestPart
            - **thumbnail**: 썸네일 이미지
            """)
    @PatchMapping(value = "/{announcementId}", consumes = {"application/json", "multipart/form-data"})
    public ApiResponse<Void> updateAnnouncement(@PathVariable Long announcementId,
                                   @RequestPart(name = "body") CommonAnnouncementRequest requestBody,
                                   @RequestPart(name = "thumbnail") MultipartFile thumbnail) {

        announcementService.updateAnnouncement(announcementId, requestBody, thumbnail);
        return ApiResponse.onSuccess();
    }

    @Tag(name = "총동연 공지사항", description = "총동연 공지사항 관련 API")
    @Operation(summary = "[총동연 공지사항] 총동연 공지사항 삭제", description = """
            총동연 공지사항을 삭제합니다.
            ### PathVariable
            - **id**: 삭제를 희망하는 공지사항의 id
            """)
    @DeleteMapping("/{announcementId}")
    public ApiResponse<Void> deleteAnnouncement(@PathVariable Long announcementId) {

        announcementService.deleteAnnouncement(announcementId);
        return ApiResponse.onSuccess();
    }


}

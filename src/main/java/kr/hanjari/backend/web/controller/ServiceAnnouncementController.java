package kr.hanjari.backend.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.service.serviceAnnouncement.ServiceAnnouncementCommandService;
import kr.hanjari.backend.service.serviceAnnouncement.ServiceAnnouncementQueryService;
import kr.hanjari.backend.web.dto.serviceAnnouncement.request.CreateServiceAnnouncementRequestDTO;
import kr.hanjari.backend.web.dto.serviceAnnouncement.response.ServiceAnnouncementDetailDTO;
import kr.hanjari.backend.web.dto.serviceAnnouncement.response.ServiceAnnouncementSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/service-announcements")
@RestController
@RequiredArgsConstructor
public class ServiceAnnouncementController {

    private final ServiceAnnouncementQueryService serviceAnnouncementQueryService;
    private final ServiceAnnouncementCommandService serviceAnnouncementCommandService;

    /* ----------------------------- 서비스 공지사항 ---------------------------------*/

    // 모든 서비스 공지사항 조회
    @Tag(name = "서비스 공지사항", description = "서비스 공지사항 관련 API")
    @Operation(summary = "[서비스 공지사항] 모든 서비스 공지사항 조회", description = """
            모든 서비스 공지사항을 페이징 조회합니다. 제목, 내용, 작성 일자를 반환합니다.
            공지사항 작성 일자를 기준으로 내림차순 정렬합니다. (최근 생성한 공지사항이 먼저 반환)
            ### Query Parameters
            - **page** : 페이지 번호 (0부터 시작)
            - **size** : 한 페이지에 보여질 아이템 개수
            """)
    @GetMapping
    public ApiResponse<ServiceAnnouncementSearchDTO> getAllServiceAnnouncements(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return ApiResponse.onSuccess(serviceAnnouncementQueryService.getAllServiceAnnouncements(page, size));
    }

    // 특정 서비스 공지사항 조회
    @Tag(name = "서비스 공지사항", description = "서비스 공지사항 관련 API")
    @Operation(summary = "[서비스 공지사항] 특정 서비스 공지사항 조회", description = """
            특정 서비스 공지사항을 조회합니다. 제목, 내용, 작성 일자를 반환합니다.
            ### Path Variables
            - **id** : 조회 할 서비스 공지사항 ID
            """)
    @GetMapping("/{id}")
    public ApiResponse<ServiceAnnouncementDetailDTO> getServiceAnnouncement(@PathVariable Long id) {
        return ApiResponse.onSuccess(serviceAnnouncementQueryService.getServiceAnnouncement(id));
    }

    // 서비스 공지사항 생성
    @Tag(name = "서비스 공지사항", description = "서비스 공지사항 관련 API")
    @Operation(summary = "[서비스 공지사항] 서비스 공지사항 생성", description = """
            서비스 공지사항을 생성합니다.
            ### Request Body
            - **title** : 제목
            - **content** : 내용
            """)
    @PostMapping("/service-admin")
    public ApiResponse<?> createServiceAnnouncement(
            @RequestBody CreateServiceAnnouncementRequestDTO requestDTO) {
        return ApiResponse.onSuccess(serviceAnnouncementCommandService.createServiceAnnouncement(requestDTO));
    }

    // 특정 서비스 공지사항 수정
    @Tag(name = "서비스 공지사항", description = "서비스 공지사항 관련 API")
    @Operation(summary = "[서비스 공지사항] 특정 서비스 공지사항 수정", description = """
            특정 서비스 공지사항을 수정합니다.
            ### Path Variables
            - **id** : 수정 할 서비스 공지사항 ID
            ### Request Body
            - **title** : 제목
            - **content** : 내용
            """)
    @PatchMapping("/service-admin/{id}")
    public ApiResponse<?> updateServiceAnnouncement(
            @PathVariable Long id,
            @RequestBody CreateServiceAnnouncementRequestDTO requestDTO) {
        return ApiResponse.onSuccess(serviceAnnouncementCommandService.updateServiceAnnouncement(id, requestDTO));
    }

    // 특정 서비스 공지사항 삭제
    @Tag(name = "서비스 공지사항", description = "서비스 공지사항 관련 API")
    @Operation(summary = "[서비스 공지사항] 특정 서비스 공지사항 삭제", description = """
            특정 서비스 공지사항을 삭제합니다.
            ### Path Variables
            - **id** : 삭제 할 서비스 공지사항 ID
            """)
    @DeleteMapping("/service-admin/{id}")
    public ApiResponse<?> deleteServiceAnnouncement(@PathVariable Long id) {
        serviceAnnouncementCommandService.deleteServiceAnnouncement(id);
        return ApiResponse.onSuccess();
    }
}

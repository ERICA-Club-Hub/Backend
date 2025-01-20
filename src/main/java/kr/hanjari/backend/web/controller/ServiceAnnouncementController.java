package kr.hanjari.backend.web.controller;

import static kr.hanjari.backend.web.dto.serviceAnnouncement.ServiceAnnouncementResponseDTO.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.web.dto.serviceAnnouncement.ServiceAnnouncementRequestDTO;
import kr.hanjari.backend.web.dto.serviceAnnouncement.ServiceAnnouncementRequestDTO.CreateServiceAnnouncementRequestDTO;
import kr.hanjari.backend.web.dto.serviceAnnouncement.ServiceAnnouncementResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/api/service-announcements")
@RestController
@RequiredArgsConstructor
public class ServiceAnnouncementController {

    /* ----------------------------- 서비스 공지사항 ---------------------------------*/

    // 모든 서비스 공지사항 조회
    @Tag(name = "서비스 공지사항", description = "서비스 공지사항 관련 API")
    @Operation(summary = "[서비스 공지사항] 모든 서비스 공지사항 조회", description = """
            모든 서비스 공지사항을 조회합니다. 제목, 내용, 작성 일자를 반환합니다.
            """)
    @GetMapping
    public ApiResponse<ServiceAnnouncementSearchDTO> getAllServiceAnnouncements() {
        return null;
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
        return null;
    }

    // 서비스 공지사항 생성
    @Tag(name = "서비스 공지사항", description = "서비스 공지사항 관련 API")
    @Operation(summary = "[서비스 공지사항] 서비스 공지사항 생성", description = """
            서비스 공지사항을 생성합니다.
            ### Request Body
            - **title** : 제목
            - **content** : 내용
            """)
    @PostMapping
    public ApiResponse<?> createServiceAnnouncement(
            @RequestBody CreateServiceAnnouncementRequestDTO requestDTO) {
        return null;
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
    @PatchMapping("/{id}")
    public ApiResponse<?> updateServiceAnnouncement(
            @PathVariable Long id,
            @RequestBody CreateServiceAnnouncementRequestDTO requestDTO) {
        return null;
    }

    // 특정 서비스 공지사항 삭제
    @Tag(name = "서비스 공지사항", description = "서비스 공지사항 관련 API")
    @Operation(summary = "[서비스 공지사항] 특정 서비스 공지사항 삭제", description = """
            특정 서비스 공지사항을 삭제합니다.
            ### Path Variables
            - **id** : 삭제 할 서비스 공지사항 ID
            """)
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteServiceAnnouncement(@PathVariable Long id) {
        return null;
    }
}

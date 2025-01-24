package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.web.dto.announcement.AnnouncementRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

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
    public void createAnnouncement(@RequestPart(name = "body") AnnouncementRequestDTO.CommonAnnouncement commonAnnouncement,
                                   @RequestPart(name = "thumbnail") MultipartFile thumbnail) {
        return;
    }

    @Tag(name = "총동연 공지사항", description = "총동연 공지사항 관련 API")
    @Operation(summary = "[총동연 공지사항] 총동연 공지사항 전체 조회", description = """
            총동연 공지사항을 전부 조회합니다.
            """)
    @GetMapping("/")
    public void getAllAnnouncement() {
        return;
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
    public void updateAnnouncement(@PathVariable Long announcementId,
                                   @RequestPart(name = "body") AnnouncementRequestDTO.CommonAnnouncement commonAnnouncement,
                                   @RequestPart(name = "thumbnail") MultipartFile thumbnail) {
        return;
    }

    @Tag(name = "총동연 공지사항", description = "총동연 공지사항 관련 API")
    @Operation(summary = "[총동연 공지사항] 총동연 공지사항 삭제", description = """
            총동연 공지사항을 삭제합니다.
            ### PathVariable
            - **id**: 삭제를 희망하는 공지사항의 id
            """)
    @DeleteMapping("/{announcementId}")
    public void deleteAnnouncement(@PathVariable Long announcementId) {
        return;
    }


}

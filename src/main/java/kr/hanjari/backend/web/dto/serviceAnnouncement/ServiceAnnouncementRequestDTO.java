package kr.hanjari.backend.web.dto.serviceAnnouncement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ServiceAnnouncementRequestDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateServiceAnnouncementRequestDTO {
        @Schema(description = "공지사항 제목", example = "공지사항 제목")
        private String title;
        @Schema(description = "공지사항 내용", example = "공지사항 내용")
        private String content;
    }
}

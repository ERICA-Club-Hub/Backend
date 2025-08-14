package kr.hanjari.backend.web.dto.club.response;

import java.util.List;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;

public record ClubSearchResponseDTO(
        List<ClubSearchResult> content,
        long totalElements,
        int page,
        int size,
        int totalPages
) {
    public static ClubSearchResponseDTO of(List<ClubSearchResult> content, long totalElements, int page, int size) {
        int totalPages = size > 0 ? (int) Math.ceil((double) totalElements / size) : 1;
        return new ClubSearchResponseDTO(content, totalElements, page, size, totalPages);
    }

    public static ClubSearchResponseDTO of(org.springframework.data.domain.Page<ClubSearchResult> page) {
        return new ClubSearchResponseDTO(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );
    }

    public record ClubSearchResult(
            Long id,
            String name,
            String oneLiner,
            String profileImageUrl,
            String categoryName,
            RecruitmentStatus recruitmentStatus
    ) {
        public static ClubSearchResult of(Long id, String name, String oneLiner, String profileImageUrl,
                                          String categoryName, RecruitmentStatus recruitmentStatus) {
            return new ClubSearchResult(id, name, oneLiner, profileImageUrl, categoryName, recruitmentStatus);
        }
    }
}
package kr.hanjari.backend.domain.club.presentation.dto.response;

import java.util.List;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;

public record ClubSearchResponse(
        List<ClubSearchResult> content,
        long totalElements,
        int page,
        int size,
        int totalPages
) {
    public static ClubSearchResponse of(List<ClubSearchResult> content, long totalElements, int page, int size) {
        int totalPages = size > 0 ? (int) Math.ceil((double) totalElements / size) : 1;
        return new ClubSearchResponse(content, totalElements, page, size, totalPages);
    }

    public static ClubSearchResponse of(org.springframework.data.domain.Page<ClubSearchResult> page) {
        return new ClubSearchResponse(
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
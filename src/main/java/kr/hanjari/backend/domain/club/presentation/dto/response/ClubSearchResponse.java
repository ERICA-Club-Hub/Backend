package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;

@Schema(description = "DTO for club search response")
public record ClubSearchResponse(
        @Schema(description = "List of club search results", nullable = false)
        List<ClubSearchResult> content,
        @Schema(description = "Total number of elements", nullable = false, example = "100")
        Long totalElements,
        @Schema(description = "Current page number (0-indexed)", nullable = false, example = "0")
        Integer page,
        @Schema(description = "Number of elements per page", nullable = false, example = "10")
        Integer size,
        @Schema(description = "Total number of pages", nullable = false, example = "10")
        Integer totalPages
) {
    public static ClubSearchResponse of(List<ClubSearchResult> content, Long totalElements, Integer page, Integer size) {
        Integer totalPages = size > 0 ? (int) Math.ceil((double) totalElements / size) : 1;
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

    @Schema(description = "Club search result item")
    public record ClubSearchResult(
            @Schema(description = "Club ID", nullable = false, example = "1")
            Long id,
            @Schema(description = "Club name", nullable = false, example = "Hanjari")
            String name,
            @Schema(description = "One-liner introduction", nullable = false, example = "The best central club at Hanyang University ERICA")
            String oneLiner,
            @Schema(description = "Club profile image URL", nullable = true, example = "https://.../profile.png")
            String profileImageUrl,
            @Schema(description = "Category name", nullable = false, example = "Academic")
            String categoryName,
            @Schema(description = "Recruitment status", nullable = false, example = "RECRUITING")
            RecruitmentStatus recruitmentStatus,
            @Schema(description = "Category tag", nullable = false, example = "Academic")
            String tag
    ) {
        public static ClubSearchResult of(Long id, String name, String oneLiner, String profileImageUrl,
                                          String categoryName, RecruitmentStatus recruitmentStatus, String tag) {
            return new ClubSearchResult(id, name, oneLiner, profileImageUrl, categoryName, recruitmentStatus, tag);
        }
    }
}
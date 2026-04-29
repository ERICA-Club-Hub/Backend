package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import org.springframework.data.domain.Page;

@Schema(description = "DTO for club detail list response")
public record ClubDetailListResponse(
        @Schema(description = "List of club responses", nullable = false)
        List<ClubResponse> clubs,
        @Schema(description = "Total number of elements", nullable = false, example = "100")
        Integer totalElements
) {
    public static ClubDetailListResponse of(Page<Club> clubs, List<String> profileImageUrls,
                                            Map<Long, List<String>> tagsMap) {
        List<ClubResponse> clubResponseList = IntStream.range(0, clubs.getContent().size())
                .mapToObj(i -> {
                    Club club = clubs.getContent().get(i);
                    return ClubResponse.of(club, profileImageUrls.get(i),
                            tagsMap.getOrDefault(club.getId(), Collections.emptyList()));
                })
                .toList();

        return new ClubDetailListResponse(clubResponseList, (int) clubs.getTotalElements());
    }
}

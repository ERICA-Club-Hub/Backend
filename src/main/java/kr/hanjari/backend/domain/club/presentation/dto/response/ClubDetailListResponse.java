package kr.hanjari.backend.domain.club.presentation.dto.response;

import java.util.List;
import java.util.stream.IntStream;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import org.springframework.data.domain.Page;

public record ClubDetailListResponse(
        List<ClubResponse> clubs,
        int totalElements
) {
    public static ClubDetailListResponse of(Page<Club> clubs, List<String> profileImageUrls) {
        List<ClubResponse> clubResponseList = IntStream.range(0, clubs.getContent().size())
                .mapToObj(i -> ClubResponse.of(clubs.getContent().get(i), profileImageUrls.get(i)))
                .toList();

        return new ClubDetailListResponse(clubResponseList, (int) clubs.getTotalElements());
    }
}

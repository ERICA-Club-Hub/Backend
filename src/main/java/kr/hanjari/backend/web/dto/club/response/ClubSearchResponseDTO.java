package kr.hanjari.backend.web.dto.club.response;

import java.util.List;
import java.util.stream.IntStream;
import kr.hanjari.backend.domain.Club;
import org.springframework.data.domain.Page;

public record ClubSearchResponseDTO(
        List<ClubResponseDTO> clubs,
        int totalElements
) {
    public static ClubSearchResponseDTO of(Page<Club> clubs, List<String> profileImageUrls) {
        List<ClubResponseDTO> clubResponseList = IntStream.range(0, clubs.getContent().size())
                .mapToObj(i -> ClubResponseDTO.of(clubs.getContent().get(i), profileImageUrls.get(i)))
                .toList();

        return new ClubSearchResponseDTO(clubResponseList, (int) clubs.getTotalElements());
    }
}

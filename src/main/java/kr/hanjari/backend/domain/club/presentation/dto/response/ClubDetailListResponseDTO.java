package kr.hanjari.backend.domain.club.presentation.dto.response;

import java.util.List;
import java.util.stream.IntStream;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import org.springframework.data.domain.Page;

public record ClubDetailListResponseDTO(
        List<ClubResponseDTO> clubs,
        int totalElements
) {
    public static ClubDetailListResponseDTO of(Page<Club> clubs, List<String> profileImageUrls) {
        List<ClubResponseDTO> clubResponseList = IntStream.range(0, clubs.getContent().size())
                .mapToObj(i -> ClubResponseDTO.of(clubs.getContent().get(i), profileImageUrls.get(i)))
                .toList();

        return new ClubDetailListResponseDTO(clubResponseList, (int) clubs.getTotalElements());
    }
}

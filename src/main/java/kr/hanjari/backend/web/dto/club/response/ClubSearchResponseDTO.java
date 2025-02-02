package kr.hanjari.backend.web.dto.club.response;

import java.util.List;
import kr.hanjari.backend.domain.Club;
import org.springframework.data.domain.Page;

public record ClubSearchResponseDTO(
        List<ClubResponseDTO> clubs,
        int totalElements
) {
    public static ClubSearchResponseDTO of(Page<Club> clubs) {
        return new ClubSearchResponseDTO(
                clubs.map(ClubResponseDTO::of).getContent(),
                (int) clubs.getTotalElements()
        );
    }
}

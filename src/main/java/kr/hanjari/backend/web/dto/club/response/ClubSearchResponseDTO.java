package kr.hanjari.backend.web.dto.club.response;

import java.util.List;

public record ClubSearchResponseDTO(
        List<ClubResponseDTO> clubs,
        int totalElements
) {
}

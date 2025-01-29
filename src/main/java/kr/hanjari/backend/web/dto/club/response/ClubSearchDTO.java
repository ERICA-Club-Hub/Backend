package kr.hanjari.backend.web.dto.club.response;

import java.util.List;

public record ClubSearchDTO(
        List<ClubDTO> clubs,
        int totalElements
) {
}

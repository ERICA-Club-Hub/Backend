package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.web.dto.club.ClubResponseDTO;

public record ClubDetailDTO(
        ClubResponseDTO.ClubDTO club,
        String profileImage,
        String leaderName,
        String leaderEmail,
        String leaderPhone
) {
}

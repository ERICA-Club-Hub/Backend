package kr.hanjari.backend.web.dto.club.response;

public record ClubDetailResponseDTO(
        ClubResponseDTO.ClubDTO club,
        String profileImage,
        String leaderName,
        String leaderEmail,
        String leaderPhone
) {
}

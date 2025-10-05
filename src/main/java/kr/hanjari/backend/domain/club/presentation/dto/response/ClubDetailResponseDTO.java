package kr.hanjari.backend.domain.club.presentation.dto.response;

public record ClubDetailResponseDTO(
        ClubResponseDTO club,
        String profileImage,
        String leaderName,
        String leaderEmail,
        String leaderPhone
) {
}

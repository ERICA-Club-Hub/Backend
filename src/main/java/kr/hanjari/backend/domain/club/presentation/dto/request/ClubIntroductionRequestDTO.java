package kr.hanjari.backend.domain.club.presentation.dto.request;

public record ClubIntroductionRequestDTO(
        String introduction,
        String activity,
        String recruitment
) {
}

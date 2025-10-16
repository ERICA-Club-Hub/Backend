package kr.hanjari.backend.domain.club.presentation.dto.request;

public record ClubIntroductionRequest(
        String introduction,
        String activity,
        String recruitment
) {
}

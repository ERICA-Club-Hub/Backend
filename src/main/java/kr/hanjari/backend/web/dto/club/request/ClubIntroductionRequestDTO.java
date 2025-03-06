package kr.hanjari.backend.web.dto.club.request;

public record ClubIntroductionRequestDTO(
        String introduction,
        String activity,
        String recruitment
) {
}

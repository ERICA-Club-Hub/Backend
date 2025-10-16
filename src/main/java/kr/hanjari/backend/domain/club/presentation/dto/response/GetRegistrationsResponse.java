package kr.hanjari.backend.domain.club.presentation.dto.response;

import java.util.List;

public record GetRegistrationsResponse(
        List<ClubRegistrationResponse> clubRegistrationResponseDTOList
) {
    public static GetRegistrationsResponse of(List<ClubRegistrationResponse> clubRegistrationResponseDTOList) {
        return new GetRegistrationsResponse(clubRegistrationResponseDTOList);
    }
}

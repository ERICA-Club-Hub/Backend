package kr.hanjari.backend.domain.club.presentation.dto.response;

import java.util.List;

public record GetRegistrationsResponseDTO(
        List<ClubRegistrationResponse> clubRegistrationResponseDTOList
) {
    public static GetRegistrationsResponseDTO of(List<ClubRegistrationResponse> clubRegistrationResponseDTOList) {
        return new GetRegistrationsResponseDTO(clubRegistrationResponseDTOList);
    }
}

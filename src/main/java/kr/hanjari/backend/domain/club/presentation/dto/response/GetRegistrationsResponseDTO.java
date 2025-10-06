package kr.hanjari.backend.domain.club.presentation.dto.response;

import java.util.List;

public record GetRegistrationsResponseDTO(
        List<ClubRegistrationDTO> clubRegistrationDTOList
) {
    public static GetRegistrationsResponseDTO of(List<ClubRegistrationDTO> clubRegistrationDTOList) {
        return new GetRegistrationsResponseDTO(clubRegistrationDTOList);
    }
}

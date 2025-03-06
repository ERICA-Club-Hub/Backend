package kr.hanjari.backend.web.dto.club.response;

import java.util.List;

public record GetRegistrationsResponseDTO(
        List<ClubRegistrationDTO> clubRegistrationDTOList
) {
    public static GetRegistrationsResponseDTO of(List<ClubRegistrationDTO> clubRegistrationDTOList) {
        return new GetRegistrationsResponseDTO(clubRegistrationDTOList);
    }
}

package kr.hanjari.backend.domain.club.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO for getting registrations response")
public record GetRegistrationsResponse(
        @Schema(description = "List of club registration responses", nullable = false)
        List<ClubRegistrationResponse> clubRegistrationResponseDTOList
) {
    public static GetRegistrationsResponse of(List<ClubRegistrationResponse> clubRegistrationResponseDTOList) {
        return new GetRegistrationsResponse(clubRegistrationResponseDTOList);
    }
}

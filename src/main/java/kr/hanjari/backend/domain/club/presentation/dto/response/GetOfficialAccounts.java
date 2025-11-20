package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.presentation.dto.ClubInstaAccountDTO;

import java.util.List;

public record GetOfficialAccounts(
        List<ClubInstaAccountDTO> officialAccounts
) {
    public static GetOfficialAccounts of(List<ClubInstaAccountDTO> officialAccounts) {
        return new GetOfficialAccounts(officialAccounts);
    }
}

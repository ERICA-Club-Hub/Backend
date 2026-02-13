package kr.hanjari.backend.domain.club.presentation.dto.response;

import java.util.List;

public record GetInstagramsMain(
        List<GetInstagrams.ClubInstagramDTO> officialAccounts
) {
    public static GetInstagramsMain of(List<GetInstagrams.ClubInstagramDTO> officialAccounts) {
        return new GetInstagramsMain(officialAccounts);
    }
}

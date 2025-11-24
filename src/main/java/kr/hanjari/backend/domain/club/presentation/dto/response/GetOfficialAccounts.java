package kr.hanjari.backend.domain.club.presentation.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record GetOfficialAccounts(
        List<ClubInstagramDTO> officialAccounts,
        Long totalElements,
        Integer page,
        Integer size,
        Integer totalPage
) {
    public static GetOfficialAccounts from(Page<ClubInstagramDTO> page) {
        return new GetOfficialAccounts(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );
    }

    public record ClubInstagramDTO(
            String clubName,
            String accountName,
            String profileImage,
            String instagramProfileUrl
    ) {
        public static ClubInstagramDTO of(String clubName, String accountName, String profileImage, String instagramProfileUrl) {
            return new ClubInstagramDTO(clubName, accountName, profileImage, instagramProfileUrl);
        }
    }
}

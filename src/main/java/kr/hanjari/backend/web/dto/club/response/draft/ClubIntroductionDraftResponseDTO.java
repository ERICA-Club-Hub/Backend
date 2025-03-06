package kr.hanjari.backend.web.dto.club.response.draft;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.draft.IntroductionDraft;
import kr.hanjari.backend.web.dto.club.response.ClubResponseDTO;

public record ClubIntroductionDraftResponseDTO(
        ClubResponseDTO club,
        String introduction,
        String activity,
        String recruitment) {
    public static ClubIntroductionDraftResponseDTO of(Club club, IntroductionDraft introductionDraft, String profileImageUrl) {
        return new ClubIntroductionDraftResponseDTO(
                ClubResponseDTO.of(club, profileImageUrl),
                introductionDraft.getContent1(),
                introductionDraft.getContent2(),
                introductionDraft.getContent3()
        );
    }

}

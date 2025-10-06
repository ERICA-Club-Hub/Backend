package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.draft.IntroductionDraft;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubResponseDTO;

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

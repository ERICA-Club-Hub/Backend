package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.draft.IntroductionDraft;

public record ClubIntroductionDraftResponseDTO(
        String introduction,
        String activity,
        String recruitment) {
    public static ClubIntroductionDraftResponseDTO of(IntroductionDraft introductionDraft) {
        return new ClubIntroductionDraftResponseDTO(
                introductionDraft.getContent1(),
                introductionDraft.getContent2(),
                introductionDraft.getContent3()
        );
    }

}

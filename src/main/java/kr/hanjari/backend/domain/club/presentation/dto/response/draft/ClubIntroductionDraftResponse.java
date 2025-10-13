package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.draft.IntroductionDraft;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubResponse;

public record ClubIntroductionDraftResponse(
        ClubResponse club,
        String introduction,
        String activity,
        String recruitment) {
    public static ClubIntroductionDraftResponse of(Club club, IntroductionDraft introductionDraft,
                                                   String profileImageUrl) {
        return new ClubIntroductionDraftResponse(
                ClubResponse.of(club, profileImageUrl),
                introductionDraft.getContent1(),
                introductionDraft.getContent2(),
                introductionDraft.getContent3()
        );
    }

}

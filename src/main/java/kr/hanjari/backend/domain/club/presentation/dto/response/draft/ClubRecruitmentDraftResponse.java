package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.draft.RecruitmentDraft;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubResponse;

public record ClubRecruitmentDraftResponse(
        ClubResponse club,
        String due,
        String notice,
        String etc) {
    public static ClubRecruitmentDraftResponse of(Club club, RecruitmentDraft recruitment, String profileImageUrl) {
        return new ClubRecruitmentDraftResponse(
                ClubResponse.of(club, profileImageUrl),
                recruitment.getContent1(),
                recruitment.getContent2(),
                recruitment.getContent3()
        );
    }

}

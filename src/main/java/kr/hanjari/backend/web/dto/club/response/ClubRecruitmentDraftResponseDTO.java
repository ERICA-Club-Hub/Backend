package kr.hanjari.backend.web.dto.club.response;

import kr.hanjari.backend.domain.Recruitment;
import kr.hanjari.backend.domain.draft.RecruitmentDraft;

public record ClubRecruitmentDraftResponseDTO(
        String due,
        String notice,
        String etc )
{
    public static ClubRecruitmentDraftResponseDTO of(RecruitmentDraft recruitment) {
        return new ClubRecruitmentDraftResponseDTO(
                recruitment.getContent1(),
                recruitment.getContent2(),
                recruitment.getContent3()
        );
    }

}

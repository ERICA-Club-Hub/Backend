package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.draft.RecruitmentDraft;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubResponseDTO;

public record ClubRecruitmentDraftResponseDTO(
        ClubResponseDTO club,
        String due,
        String notice,
        String etc )
{
    public static ClubRecruitmentDraftResponseDTO of(Club club, RecruitmentDraft recruitment, String profileImageUrl) {
        return new ClubRecruitmentDraftResponseDTO(
                ClubResponseDTO.of(club, profileImageUrl),
                recruitment.getContent1(),
                recruitment.getContent2(),
                recruitment.getContent3()
        );
    }

}

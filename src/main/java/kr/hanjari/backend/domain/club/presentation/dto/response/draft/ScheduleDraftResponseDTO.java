package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import kr.hanjari.backend.domain.club.domain.entity.draft.ScheduleDraft;

public record ScheduleDraftResponseDTO (Long id, Integer month, String content){
    public static ScheduleDraftResponseDTO of(ScheduleDraft schedule) {
        return new ScheduleDraftResponseDTO(schedule.getId(), schedule.getMonth(), schedule.getContent());
    }
}

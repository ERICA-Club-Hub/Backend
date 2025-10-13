package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import kr.hanjari.backend.domain.club.domain.entity.draft.ScheduleDraft;

public record ScheduleDraftResponse(Long id, Integer month, String content) {
    public static ScheduleDraftResponse of(ScheduleDraft schedule) {
        return new ScheduleDraftResponse(schedule.getId(), schedule.getMonth(), schedule.getContent());
    }
}

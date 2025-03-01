package kr.hanjari.backend.web.dto.club.response.draft;

import kr.hanjari.backend.domain.Schedule;
import kr.hanjari.backend.domain.draft.ScheduleDraft;

public record ScheduleDraftResponseDTO (Long id, Integer month, String content){
    public static ScheduleDraftResponseDTO of(ScheduleDraft schedule) {
        return new ScheduleDraftResponseDTO(schedule.getId(), schedule.getMonth(), schedule.getContent());
    }
}

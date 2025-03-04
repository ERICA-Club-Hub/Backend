package kr.hanjari.backend.web.dto.club.response.draft;

import java.util.List;
import kr.hanjari.backend.domain.Schedule;
import kr.hanjari.backend.domain.draft.ScheduleDraft;

public record ClubScheduleDraftResponseDTO (List<ScheduleDraftResponseDTO> schedules, Integer totalElements){
    public static ClubScheduleDraftResponseDTO of(List<ScheduleDraft> schecduleList) {
        return new ClubScheduleDraftResponseDTO(
            schecduleList.stream().map(ScheduleDraftResponseDTO::of).toList(),
            schecduleList.size()
        );
    }
}

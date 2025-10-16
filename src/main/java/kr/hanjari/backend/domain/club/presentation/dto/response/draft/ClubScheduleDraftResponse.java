package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.draft.ScheduleDraft;

public record ClubScheduleDraftResponse(List<ScheduleDraftResponse> schedules, Integer totalElements) {
    public static ClubScheduleDraftResponse of(List<ScheduleDraft> schecduleList) {
        return new ClubScheduleDraftResponse(
                schecduleList.stream().map(ScheduleDraftResponse::of).toList(),
                schecduleList.size()
        );
    }
}

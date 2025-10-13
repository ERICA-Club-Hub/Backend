package kr.hanjari.backend.domain.club.presentation.dto.request;

import java.util.List;

public record ClubScheduleListRequest(List<ClubScheduleRequest> schedules) {
}

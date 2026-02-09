package kr.hanjari.backend.domain.club.application.event;

public record RecruitmentStatusOpenedEvent(
        Long clubId,
        String clubName
) {
}

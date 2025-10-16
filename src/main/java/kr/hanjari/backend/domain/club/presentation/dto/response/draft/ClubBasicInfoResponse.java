package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import kr.hanjari.backend.domain.club.domain.entity.Club;

public record ClubBasicInfoResponse(
        String leaderName,
        String leaderEmail,
        String leaderPhone,
        String activities,
        Integer membershipFee,
        String snsUrl
) {
    public static ClubBasicInfoResponse of(Club club) {
        return new ClubBasicInfoResponse(
                club.getLeaderName(),
                club.getLeaderEmail(),
                club.getLeaderPhone(),
                club.getMeetingSchedule(),
                club.getMembershipFee(),
                club.getSnsUrl()
        );
    }
}

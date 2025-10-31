package kr.hanjari.backend.domain.club.presentation.dto.response.draft;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hanjari.backend.domain.club.domain.entity.Club;

@Schema(description = "DTO for club basic information response (draft)")
public record ClubBasicInfoResponse(
        @Schema(description = "Leader's name", nullable = true, example = "Gildong Hong")
        String leaderName,
        @Schema(description = "Leader's email", nullable = true, example = "leader@example.com")
        String leaderEmail,
        @Schema(description = "Leader's phone number", nullable = true, example = "010-1234-5678")
        String leaderPhone,
        @Schema(description = "Club activities", nullable = true, example = "Regular meeting every Monday")
        String activities,
        @Schema(description = "Membership fee", nullable = true, example = "10000")
        Integer membershipFee,
        @Schema(description = "SNS URL", nullable = true, example = "https://www.instagram.com/hanjari_")
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

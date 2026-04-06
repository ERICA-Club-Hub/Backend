package kr.hanjari.backend.domain.club.presentation.dto.response;

import kr.hanjari.backend.domain.club.domain.entity.Club;

public record ClubDetailResponse(
    Long clubId,
    String description,
    String leaderName,
    String leaderPhone,
    String contactEmail,
    String membershipFee,
    String snsAccount,
    String applicationUrl

) {
  public static ClubDetailResponse from(Club club) {
    return new ClubDetailResponse(
            club.getId(),
            club.getDescription(),
            club.getLeaderName(),
            club.getLeaderPhone(),
            club.getLeaderEmail(),
            club.getMembershipFee(),
            club.getSnsUrl(),
            club.getApplicationUrl()
    );
  }


  public static ClubDetailResponse of(
      Long clubId,
      String description,
      String leaderName,
      String leaderPhone,
      String contactEmail,
      String membershipFee,
      String snsAccount,
      String applicationUrl
  ) {
    return new ClubDetailResponse(
        clubId,
        description,
        leaderName,
        leaderPhone,
        contactEmail,
        membershipFee,
        snsAccount,
        applicationUrl
    );
  }

}

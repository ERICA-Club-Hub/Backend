package kr.hanjari.backend.domain.club.presentation.dto.response;

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

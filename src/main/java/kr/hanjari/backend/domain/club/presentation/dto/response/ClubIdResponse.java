package kr.hanjari.backend.domain.club.presentation.dto.response;

import java.util.List;

public record ClubIdResponse(
    List<Long> clubIds,
    long totalElements
) {

  public static ClubIdResponse of(
      List<Long> clubIds,
      long totalElements
  ) {
    return new ClubIdResponse(clubIds, totalElements);
  }
}

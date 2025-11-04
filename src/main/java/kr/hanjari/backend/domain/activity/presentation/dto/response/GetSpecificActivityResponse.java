package kr.hanjari.backend.domain.activity.presentation.dto.response;

import kr.hanjari.backend.domain.activity.domain.entity.Activity;
import kr.hanjari.backend.domain.activity.presentation.dto.ActivityImageDTO;

import java.time.LocalDate;
import java.util.List;

public record GetSpecificActivityResponse(
        String clubName,
        String clubImageUrl,
        String content,
        LocalDate date,
        List<ActivityImageDTO> activityImageDTOList
) {
    public static GetSpecificActivityResponse of(Activity activity, String clubImageUrl, List<ActivityImageDTO> activityImageUrlList) {
        return new GetSpecificActivityResponse(activity.getContent(), activity.getClub().getName(),
                clubImageUrl, activity.getDate(), activityImageUrlList);
    }
}

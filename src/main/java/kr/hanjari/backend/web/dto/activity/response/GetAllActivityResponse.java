package kr.hanjari.backend.web.dto.activity.response;

import java.util.List;

public record GetAllActivityResponse(
        List<String> thumbnailUrlList
) {
    public static GetAllActivityResponse of(List<String> thumbnailUrlList) {
        return new GetAllActivityResponse(thumbnailUrlList);
    }
}

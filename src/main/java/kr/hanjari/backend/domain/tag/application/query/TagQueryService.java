package kr.hanjari.backend.domain.tag.application.query;

import kr.hanjari.backend.domain.tag.presentation.dto.response.GetAllTagsResponse;

public interface TagQueryService {

    GetAllTagsResponse getAllTags();
}

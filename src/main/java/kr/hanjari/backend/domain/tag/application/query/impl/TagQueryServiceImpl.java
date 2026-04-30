package kr.hanjari.backend.domain.tag.application.query.impl;

import kr.hanjari.backend.domain.tag.application.query.TagQueryService;
import kr.hanjari.backend.domain.tag.domain.repository.TagRepository;
import kr.hanjari.backend.domain.tag.presentation.dto.response.GetAllTagsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagQueryServiceImpl implements TagQueryService {

    private final TagRepository tagRepository;

    @Override
    public GetAllTagsResponse getAllTags() {
        return GetAllTagsResponse.of(tagRepository.findAllByOrderByPriorityAsc());
    }
}

package kr.hanjari.backend.domain.tag.application.command.impl;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.repository.ClubRepository;
import kr.hanjari.backend.domain.tag.application.command.TagCommandService;
import kr.hanjari.backend.domain.tag.domain.entity.ClubTag;
import kr.hanjari.backend.domain.tag.domain.entity.Tag;
import kr.hanjari.backend.domain.tag.domain.repository.ClubTagRepository;
import kr.hanjari.backend.domain.tag.domain.repository.TagRepository;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagCommandServiceImpl implements TagCommandService {

    private final TagRepository tagRepository;
    private final ClubTagRepository clubTagRepository;
    private final ClubRepository clubRepository;

    @Override
    public Long createTag(String name, Integer priority) {
        if (tagRepository.existsByName(name)) {
            throw new GeneralException(ErrorStatus._TAG_ALREADY_EXISTS);
        }
        Tag tag = Tag.builder().name(name).priority(priority).build();
        return tagRepository.save(tag).getId();
    }

    @Override
    public void addTagToClub(Long clubId, Long tagId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._TAG_NOT_FOUND));
        if (clubTagRepository.existsByClubIdAndTagId(clubId, tagId)) {
            throw new GeneralException(ErrorStatus._TAG_ALREADY_ASSIGNED);
        }
        clubTagRepository.save(ClubTag.create(club, tag));
    }

    @Override
    public void removeTagFromClub(Long clubId, Long tagId) {
        if (!clubTagRepository.existsByClubIdAndTagId(clubId, tagId)) {
            throw new GeneralException(ErrorStatus._TAG_NOT_ASSIGNED);
        }
        clubTagRepository.deleteByClubIdAndTagId(clubId, tagId);
    }
}
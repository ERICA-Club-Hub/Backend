package kr.hanjari.backend.domain.tag.application.command;

public interface TagCommandService {

    Long createTag(String name, Integer priority);

    void addTagToClub(Long clubId, Long tagId);

    void removeTagFromClub(Long clubId, Long tagId);
}
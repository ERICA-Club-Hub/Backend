package kr.hanjari.backend.domain.tag.domain.repository;

import java.util.Collection;
import java.util.List;
import kr.hanjari.backend.domain.tag.domain.entity.ClubTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubTagRepository extends JpaRepository<ClubTag, Long> {

    @Query("SELECT ct FROM ClubTag ct JOIN FETCH ct.tag t WHERE ct.club.id IN :clubIds ORDER BY t.priority ASC")
    List<ClubTag> findByClubIdsWithTagsOrderedByPriority(@Param("clubIds") Collection<Long> clubIds);

    @Query("SELECT ct FROM ClubTag ct JOIN FETCH ct.tag t WHERE ct.club.id = :clubId ORDER BY t.priority ASC")
    List<ClubTag> findByClubIdWithTagsOrderedByPriority(@Param("clubId") Long clubId);

    boolean existsByClubIdAndTagId(Long clubId, Long tagId);

    @Modifying
    @Query("DELETE FROM ClubTag ct WHERE ct.club.id = :clubId AND ct.tag.id = :tagId")
    void deleteByClubIdAndTagId(@Param("clubId") Long clubId, @Param("tagId") Long tagId);
}
package kr.hanjari.backend.domain.club.domain.repository.search;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.enums.*;
import kr.hanjari.backend.domain.club.domain.repository.search.projection.ClubSearchProjection;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface ClubSearchRepository {

    Page<Club> findCentralClubsByCondition(
            String keyword, Long tagId, SortBy sortBy,
            CentralClubCategory category, boolean onlyWithSns, int page, int size);

    Page<ClubSearchProjection> findCentralClubsAsProjection(
            String keyword, Long tagId, SortBy sortBy,
            CentralClubCategory category, boolean onlyWithSns, int page, int size);

    Page<Club> findUnionClubsByCondition(
            String keyword, Long tagId, SortBy sortBy,
            UnionClubCategory category, boolean onlyWithSns, int page, int size);

    Page<ClubSearchProjection> findUnionClubsAsProjection(
            String keyword, Long tagId, SortBy sortBy,
            UnionClubCategory category, boolean onlyWithSns, int page, int size);

    Page<Club> findCollegeClubsByCondition(
            String keyword, Long tagId, SortBy sortBy,
            College college, boolean onlyWithSns, int page, int size);

    Page<ClubSearchProjection> findCollegeClubsAsProjection(
            String keyword, Long tagId, SortBy sortBy,
            College college, boolean onlyWithSns, int page, int size);

    Page<Club> findDepartmentClubsByCondition(
            String keyword, Long tagId, SortBy sortBy,
            College college, Department departmentName, boolean onlyWithSns, int page, int size);

    Page<ClubSearchProjection> findDepartmentClubsAsProjection(
            String keyword, Long tagId, SortBy sortBy,
            College college, Department departmentName, boolean onlyWithSns, int page, int size);

    Page<Club> findPopularClubs(int page, int size);

    Page<Club> findRecentUpdateClubs(int page, int size);

    Page<Club> findClubsByType(ClubType type, boolean onlyWithSns, int page, int size);

    List<Club> findClubByRandom(int size);

    List<Club> findClubByRandomWithSns(int size);

    List<Club> findRandomClubsUpdatedAfter(LocalDateTime date, int size);
}

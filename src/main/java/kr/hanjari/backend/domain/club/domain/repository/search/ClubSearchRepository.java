package kr.hanjari.backend.domain.club.domain.repository.search;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.enums.*;
import org.springframework.data.domain.Page;

public interface ClubSearchRepository {

    Page<Club> findCentralClubsByCondition(
            String keyword, RecruitmentStatus status, SortBy sortBy,
            CentralClubCategory category, int page, int size);

    Page<Club> findUnionClubsByCondition(
            String keyword, RecruitmentStatus status, SortBy sortBy,
            UnionClubCategory category, int page, int size);

    Page<Club> findCollegeClubsByCondition(
            String keyword, RecruitmentStatus status, SortBy sortBy,
            College college, int page, int size);

    Page<Club> findDepartmentClubsByCondition(
            String keyword, RecruitmentStatus status, SortBy sortBy,
            College college, Department departmentName, int page, int size);

    Page<Club> findPopularClubs(int page, int size);

    Page<Club> findRecentUpdateClubs(int page, int size);

    Page<Club> findClubsByType(ClubType type, int page, int size);
}

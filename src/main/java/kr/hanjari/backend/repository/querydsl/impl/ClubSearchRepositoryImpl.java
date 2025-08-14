package kr.hanjari.backend.repository.querydsl.impl;

import static org.springframework.data.domain.PageRequest.of;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.QClub;
import kr.hanjari.backend.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.enums.ClubType;
import kr.hanjari.backend.domain.enums.College;
import kr.hanjari.backend.domain.enums.Department;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.enums.SortBy;
import kr.hanjari.backend.domain.enums.UnionClubCategory;
import kr.hanjari.backend.repository.querydsl.ClubSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClubSearchRepositoryImpl implements ClubSearchRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Club> findCentralClubsByCondition(String keyword, RecruitmentStatus status, SortBy sortBy,
                                                  CentralClubCategory category, int page, int size) {
        QClub club = QClub.club;
        List<Club> clubs = query.select(club)
                .from(club)
                .where(
                        club.categoryInfo.clubType.eq(ClubType.CENTRAL),
                        nameContains(keyword),
                        statusEq(status),
                        centralCategoryEq(category))
                .orderBy(getOrderSpecifier(sortBy))
                .offset((long) page * size)
                .limit(size)
                .fetch();

        Long totalElements = query.select(club.count())
                .from(club)
                .where(
                        club.categoryInfo.clubType.eq(ClubType.CENTRAL),
                        nameContains(keyword),
                        statusEq(status),
                        centralCategoryEq(category))
                .fetchOne();

        return new PageImpl<>(clubs, of(page, size), totalElements != null ? totalElements : 0);
    }

    @Override
    public Page<Club> findUnionClubsByCondition(String keyword, RecruitmentStatus status, SortBy sortBy,
                                                UnionClubCategory category, int page, int size) {
        QClub club = QClub.club;

        List<Club> clubs = query.select(club)
                .from(club)
                .where(
                        club.categoryInfo.clubType.eq(ClubType.UNION),
                        nameContains(keyword),
                        statusEq(status),
                        unionCategoryEq(category))
                .orderBy(getOrderSpecifier(sortBy))
                .offset((long) page * size)
                .limit(size)
                .fetch();

        Long totalElement = query.select(club.count())
                .from(club)
                .where(
                        club.categoryInfo.clubType.eq(ClubType.UNION),
                        nameContains(keyword),
                        statusEq(status),
                        unionCategoryEq(category))
                .fetchOne();

        return new PageImpl<>(clubs, of(page, size), totalElement != null ? totalElement : 0);
    }

    @Override
    public Page<Club> findCollegeClubsByCondition(String keyword, RecruitmentStatus status, SortBy sortBy,
                                                  College college, int page, int size) {
        QClub club = QClub.club;

        List<Club> clubs = query.select(club)
                .from(club)
                .where(
                        club.categoryInfo.clubType.eq(ClubType.COLLEGE),
                        nameContains(keyword),
                        statusEq(status),
                        collegeEq(college))
                .orderBy(getOrderSpecifier(sortBy))
                .offset((long) page * size)
                .limit(size)
                .fetch();

        Long totalElement = query.select(club.count())
                .from(club)
                .where(
                        club.categoryInfo.clubType.eq(ClubType.COLLEGE),
                        nameContains(keyword),
                        statusEq(status),
                        collegeEq(college))
                .fetchOne();

        return new PageImpl<>(clubs, of(page, size), totalElement != null ? totalElement : 0);
    }

    @Override
    public Page<Club> findDepartmentClubsByCondition(String keyword, RecruitmentStatus status, SortBy sortBy,
                                                     College college, Department departmentName, int page, int size) {
        QClub club = QClub.club;

        List<Club> clubs = query.select(club)
                .from(club)
                .where(
                        club.categoryInfo.clubType.eq(ClubType.DEPARTMENT),
                        nameContains(keyword),
                        statusEq(status),
                        collegeEq(college),
                        departmentEq(departmentName))
                .orderBy(getOrderSpecifier(sortBy))
                .offset((long) page * size)
                .limit(size)
                .fetch();

        Long totalElement = query.select(club.count())
                .from(club)
                .where(
                        club.categoryInfo.clubType.eq(ClubType.DEPARTMENT),
                        nameContains(keyword),
                        statusEq(status),
                        collegeEq(college),
                        departmentEq(departmentName))
                .fetchOne();

        return new PageImpl<>(clubs, of(page, size), totalElement != null ? totalElement : 0);
    }

    @Override
    public Page<Club> findPopularClubs(int page, int size) {
        return null;
    }

    private BooleanExpression nameContains(String keyword) {
        return keyword != null && !keyword.isBlank()
                ? QClub.club.name.containsIgnoreCase(keyword)
                : null;
    }

    private BooleanExpression statusEq(RecruitmentStatus status) {
        return status != null ? QClub.club.recruitmentStatus.eq(status) : null;
    }

    private BooleanExpression centralCategoryEq(CentralClubCategory category) {
        return category != null ? QClub.club.categoryInfo.centralCategory.eq(category) : null;
    }

    private BooleanExpression unionCategoryEq(UnionClubCategory category) {
        return category != null ? QClub.club.categoryInfo.unionCategory.eq(category) : null;
    }

    private BooleanExpression collegeEq(College college) {
        return college != null ? QClub.club.categoryInfo.college.eq(college) : null;
    }

    private BooleanExpression departmentEq(Department department) {
        return department != null ? QClub.club.categoryInfo.department.eq(department) : null;
    }


    private OrderSpecifier<?>[] getOrderSpecifier(SortBy sortBy) {
        if (sortBy == null) {
            return new OrderSpecifier<?>[]{QClub.club.name.asc()};
        }

        QClub club = QClub.club;
        return switch (sortBy) {
            case NAME_ASC -> new OrderSpecifier<?>[]{club.name.asc()};
            case CATEGORY_ASC -> new OrderSpecifier<?>[]{
                    club.categoryInfo.centralCategory.asc().nullsLast(),
                    club.categoryInfo.unionCategory.asc().nullsLast(),
                    club.categoryInfo.college.asc().nullsLast(),
                    club.categoryInfo.department.asc().nullsLast()
            };
            case RECRUITMENT_STATUS_ASC -> new OrderSpecifier<?>[]{club.recruitmentStatus.asc()};
        };
    }
}

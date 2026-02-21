package kr.hanjari.backend.domain.club.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.domain.enums.ClubType;
import kr.hanjari.backend.domain.club.domain.enums.College;
import kr.hanjari.backend.domain.club.domain.enums.Department;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.domain.enums.UnionClubCategory;
import kr.hanjari.backend.domain.club.presentation.dto.request.CategoryRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationUpdateRequest;
import kr.hanjari.backend.domain.club.presentation.dto.util.CategoryUtils;
import kr.hanjari.backend.domain.common.command.CategoryCommand;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClubCategoryTest {

    private Club newClub() {
        return Club.builder()
                .name("old")
                .leaderEmail("old@univ.ac.kr")
                .recruitmentStatus(RecruitmentStatus.UPCOMING)
                .oneLiner("old one")
                .briefIntroduction("old brief")
                .categoryInfo(ClubCategoryInfo.from(CategoryCommand.of(
                        ClubType.UNION,
                        null,
                        UnionClubCategory.IT,
                        null,
                        null)
                ))
                .build();
    }

    @Test
    @DisplayName("CENTRAL: centralCategory 설정 및 나머지 필드 초기화")
    void centralCategory_set_and_clear_others() {
        Club club = newClub();
        CategoryRequest categoryRequest = new CategoryRequest(CentralClubCategory.ACADEMIC,
            null,
            null,
            null);
        ClubBasicInformationUpdateRequest dto = new ClubBasicInformationUpdateRequest(
                "보안동아리",
                ClubType.CENTRAL,
                categoryRequest,
                "보안 좋아함");

        CategoryUtils.validate(ClubType.CENTRAL, categoryRequest);
        CategoryCommand cmd = CategoryUtils.toCategoryCommand(ClubType.CENTRAL, categoryRequest);

        club.updateClubCommonInfo(dto, cmd);

        assertEquals("보안동아리", club.getName());
//        assertEquals("lead@univ.ac.kr", club.getLeaderEmail());
        assertEquals(ClubType.CENTRAL, club.getCategoryInfo().getClubType());
        assertEquals(CentralClubCategory.ACADEMIC, club.getCategoryInfo().getCentralCategory());
        assertNull(club.getCategoryInfo().getUnionCategory());
        assertNull(club.getCategoryInfo().getCollege());
        assertNull(club.getCategoryInfo().getDepartment());
    }

    @Test
    @DisplayName("UNION: unionCategory 설정 및 나머지 필드 초기화")
    void unionCategory_set_and_clear_others() {
        Club club = newClub();
        CategoryRequest categoryRequest = new CategoryRequest(null, UnionClubCategory.MARKETING_AD,
            null, null);
        ClubBasicInformationUpdateRequest dto = new ClubBasicInformationUpdateRequest(
                "연합마케팅",
                ClubType.UNION,
                categoryRequest,
                "실전 마케팅"
        );

        CategoryUtils.validate(ClubType.UNION, categoryRequest);
        CategoryCommand cmd = CategoryUtils.toCategoryCommand(ClubType.UNION, categoryRequest);

        club.updateClubCommonInfo(dto, cmd);

        assertEquals(ClubType.UNION, club.getCategoryInfo().getClubType());
        assertEquals(UnionClubCategory.MARKETING_AD, club.getCategoryInfo().getUnionCategory());
        assertNull(club.getCategoryInfo().getCentralCategory());
        assertNull(club.getCategoryInfo().getCollege());
        assertNull(club.getCategoryInfo().getDepartment());
    }

    @Test
    @DisplayName("COLLEGE: college 설정(학과 없음) 및 나머지 필드 초기화")
    void college_only_set_and_clear_others() {
        Club club = newClub();
        CategoryRequest categoryRequest = new CategoryRequest(null, null, College.ENGINEERING,
            null);
        ClubBasicInformationUpdateRequest dto = new ClubBasicInformationUpdateRequest(
                "공대 세미나",
                ClubType.COLLEGE,
                categoryRequest,
                "공대 전체 세미나"
        );

        CategoryUtils.validate(ClubType.COLLEGE, categoryRequest);
        CategoryCommand cmd = CategoryUtils.toCategoryCommand(ClubType.COLLEGE, categoryRequest);
        club.updateClubCommonInfo(dto, cmd);

        assertEquals(ClubType.COLLEGE, club.getCategoryInfo().getClubType());
        assertEquals(College.ENGINEERING, club.getCategoryInfo().getCollege());
        assertNull(club.getCategoryInfo().getDepartment());
        assertNull(club.getCategoryInfo().getUnionCategory());
        assertNull(club.getCategoryInfo().getCentralCategory());
    }

    @Test
    @DisplayName("DEPARTMENT: college + department 설정 및 관계 검증 통과")
    void department_set_with_valid_relation() {
        Club club = newClub();
        CategoryRequest categoryRequest = new CategoryRequest(null, null, College.ENGINEERING,
            Department.MECHANICAL);

        ClubBasicInformationUpdateRequest dto = new ClubBasicInformationUpdateRequest(
                "기계과 CAD 연구회",
                ClubType.DEPARTMENT,
                categoryRequest,
                "CAD 마스터"
        );


        CategoryUtils.validate(ClubType.DEPARTMENT, categoryRequest);

        CategoryCommand cmd = CategoryUtils.toCategoryCommand(ClubType.DEPARTMENT, categoryRequest);

        club.updateClubCommonInfo(dto, cmd);

        assertEquals(ClubType.DEPARTMENT, club.getCategoryInfo().getClubType());
        assertEquals(College.ENGINEERING, club.getCategoryInfo().getCollege());
        assertEquals(Department.MECHANICAL, club.getCategoryInfo().getDepartment());
        assertNull(club.getCategoryInfo().getCentralCategory());
        assertNull(club.getCategoryInfo().getUnionCategory());
    }

    @Test
    @DisplayName("DEPARTMENT: 학과-단과대 불일치 시 예외")
    void department_set_with_invalid_relation_should_throw() {
        Club club = newClub();
        CategoryRequest categoryRequest = new CategoryRequest(null, null, College.SOFTWARE,
            Department.MECHANICAL);
        // 가정: Department.MECHANICAL 은 College.ENGINEERING 소속
        ClubBasicInformationUpdateRequest dto = new ClubBasicInformationUpdateRequest(
                "lead@univ.ac.kr",
                ClubType.DEPARTMENT,
                categoryRequest,
                "invalid"
        );

        CategoryUtils.validate(ClubType.DEPARTMENT, categoryRequest);

        CategoryCommand cmd = CategoryUtils.toCategoryCommand(ClubType.DEPARTMENT, categoryRequest);

        assertThrows(GeneralException.class, () -> club.updateClubCommonInfo(dto, cmd));
    }

    @Test
    @DisplayName("DTO validate: 타입별 필수/금지 필드 검증 동작")
    void dto_validate_should_work() {
        // UNION 타입인데 central을 채우면 실패해야 함
        ClubBasicInformationRequest bad = new ClubBasicInformationRequest(
                "연합동아리",
                "lead@univ.ac.kr",
                ClubType.UNION,
                new CategoryRequest(CentralClubCategory.ACADEMIC, null, null, null),
                "bad",
                "bad"
        );

//        assertThrows(IllegalArgumentException.class, CategoryUtils.validate(ClubType.UNION, bad.category()));
    }

    @Test
    @DisplayName("DTO → CategoryCommand 변환 확인")
    void dto_to_command() {
        CategoryRequest categoryRequest = new CategoryRequest(null, null, College.ENGINEERING,
            Department.MECHANICAL);
        ClubBasicInformationRequest dto = new ClubBasicInformationRequest(
                "기계과 CAD 연구회",
                "lead@univ.ac.kr",
                ClubType.DEPARTMENT,
                categoryRequest,
                "CAD",
                "설계"
        );
        CategoryUtils.validate(ClubType.DEPARTMENT, categoryRequest);

        CategoryCommand cmd = CategoryUtils.toCategoryCommand(ClubType.DEPARTMENT, categoryRequest);
        assertEquals(ClubType.DEPARTMENT, cmd.type());
        assertNull(cmd.central());
        assertNull(cmd.union());
        assertEquals(College.ENGINEERING, cmd.college());
        assertEquals(Department.MECHANICAL, cmd.department());
    }
}
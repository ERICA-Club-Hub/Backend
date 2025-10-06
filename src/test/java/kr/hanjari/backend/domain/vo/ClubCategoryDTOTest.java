package kr.hanjari.backend.domain.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.ClubCategoryInfo;
import kr.hanjari.backend.domain.command.CategoryCommand;
import kr.hanjari.backend.domain.club.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.enums.ClubType;
import kr.hanjari.backend.domain.club.enums.College;
import kr.hanjari.backend.domain.club.enums.Department;
import kr.hanjari.backend.domain.club.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.enums.UnionClubCategory;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationDTO;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationDTO.CategoryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClubCategoryDTOTest {

    private Club newClub() {
        return Club.builder()
                .name("old")
                .leaderEmail("old@univ.ac.kr")
                .recruitmentStatus(RecruitmentStatus.UPCOMING)
                .oneLiner("old one")
                .briefIntroduction("old brief")
                .categoryInfo(new ClubCategoryInfo())
                .build();
    }

    @Test
    @DisplayName("CENTRAL: centralCategory 설정 및 나머지 필드 초기화")
    void centralCategory_set_and_clear_others() {
        Club club = newClub();

        ClubBasicInformationDTO dto = new ClubBasicInformationDTO(
                "보안동아리",
                "lead@univ.ac.kr",
                ClubType.CENTRAL,
                new CategoryDTO(CentralClubCategory.ACADEMIC,
                        null,
                        null,
                        null),
                "보안 좋아함",
                "CTF 스터디");

        dto.validate();
        CategoryCommand cmd = dto.toCategoryCommand();

        club.updateClubCommonInfo(dto, cmd);

        assertEquals("보안동아리", club.getName());
        assertEquals("lead@univ.ac.kr", club.getLeaderEmail());
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

        ClubBasicInformationDTO dto = new ClubBasicInformationDTO(
                "연합마케팅",
                "lead@univ.ac.kr",
                ClubType.UNION,
                new CategoryDTO(null, UnionClubCategory.MARKETING_AD, null, null),
                "실전 마케팅",
                "프로모션 실습"
        );

        dto.validate();
        CategoryCommand cmd = dto.toCategoryCommand();

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

        ClubBasicInformationDTO dto = new ClubBasicInformationDTO(
                "공대 세미나",
                "lead@univ.ac.kr",
                ClubType.COLLEGE,
                new CategoryDTO(null, null, College.ENGINEERING, null),
                "공대 전체 세미나",
                "격주 세미나"
        );

        dto.validate();
        CategoryCommand cmd = dto.toCategoryCommand();

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

        ClubBasicInformationDTO dto = new ClubBasicInformationDTO(
                "기계과 CAD 연구회",
                "lead@univ.ac.kr",
                ClubType.DEPARTMENT,
                new CategoryDTO(null, null, College.ENGINEERING, Department.MECHANICAL),
                "CAD 마스터",
                "설계 툴 스터디"
        );

        dto.validate();
        CategoryCommand cmd = dto.toCategoryCommand();

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

        // 가정: Department.MECHANICAL 은 College.ENGINEERING 소속
        ClubBasicInformationDTO dto = new ClubBasicInformationDTO(
                "잘못된 소속 테스트",
                "lead@univ.ac.kr",
                ClubType.DEPARTMENT,
                new CategoryDTO(null, null, College.SOFTWARE, Department.MECHANICAL),
                "invalid",
                "invalid"
        );

        dto.validate();
        CategoryCommand cmd = dto.toCategoryCommand();

        assertThrows(GeneralException.class, () -> club.updateClubCommonInfo(dto, cmd));
    }

    @Test
    @DisplayName("DTO validate: 타입별 필수/금지 필드 검증 동작")
    void dto_validate_should_work() {
        // UNION 타입인데 central을 채우면 실패해야 함
        ClubBasicInformationDTO bad = new ClubBasicInformationDTO(
                "연합동아리",
                "lead@univ.ac.kr",
                ClubType.UNION,
                new CategoryDTO(CentralClubCategory.ACADEMIC, null, null, null),
                "bad",
                "bad"
        );

        assertThrows(IllegalArgumentException.class, bad::validate);
    }

    @Test
    @DisplayName("DTO → CategoryCommand 변환 확인")
    void dto_to_command() {
        ClubBasicInformationDTO dto = new ClubBasicInformationDTO(
                "기계과 CAD 연구회",
                "lead@univ.ac.kr",
                ClubType.DEPARTMENT,
                new CategoryDTO(null, null, College.ENGINEERING, Department.MECHANICAL),
                "CAD",
                "설계"
        );
        dto.validate();

        CategoryCommand cmd = dto.toCategoryCommand();
        assertEquals(ClubType.DEPARTMENT, cmd.type());
        assertNull(cmd.central());
        assertNull(cmd.union());
        assertEquals(College.ENGINEERING, cmd.college());
        assertEquals(Department.MECHANICAL, cmd.department());
    }
}
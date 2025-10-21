package kr.hanjari.backend.domain.club.domain.entity;

import static java.util.Objects.requireNonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.domain.enums.ClubType;
import kr.hanjari.backend.domain.club.domain.enums.College;
import kr.hanjari.backend.domain.club.domain.enums.Department;
import kr.hanjari.backend.domain.club.domain.enums.UnionClubCategory;
import kr.hanjari.backend.domain.common.command.CategoryCommand;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 공통 VO
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubCategoryInfo {

    @Enumerated(EnumType.STRING)
    @Column(name = "club_type", nullable = false)
    private ClubType clubType;

    @Enumerated(EnumType.STRING)
    @Column(name = "central_category")
    private CentralClubCategory centralCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "union_category")
    private UnionClubCategory unionCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "college")
    private College college;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Department department;

    private ClubCategoryInfo(ClubType type,
                             CentralClubCategory central,
                             UnionClubCategory union,
                             College college,
                             Department department) {
        validate(type, central, union, college, department);
        this.clubType = type;
        this.centralCategory = central;
        this.unionCategory = union;
        this.college = college;
        this.department = department;
    }

    public static ClubCategoryInfo from(CategoryCommand cmd) {
        return new ClubCategoryInfo(cmd.type(), cmd.central(), cmd.union(), cmd.college(), cmd.department());
    }

    public void apply(CategoryCommand cmd) {
        validate(cmd.type(), cmd.central(), cmd.union(), cmd.college(), cmd.department());
        this.clubType = cmd.type();
        this.centralCategory = cmd.central();
        this.unionCategory = cmd.union();
        this.college = cmd.college();
        this.department = cmd.department();
    }

    private static void validate(ClubType type,
                                 CentralClubCategory central,
                                 UnionClubCategory union,
                                 College college,
                                 Department department) {
        switch (type) {
            case CENTRAL -> {
                requireNonNull(central, "CENTRAL은 centralCategory 필수");
                ensureNull(union, college, department);
            }
            case UNION -> {
                requireNonNull(union, "UNION은 unionCategory 필수");
                ensureNull(central, college, department);
            }
            case COLLEGE -> {
                requireNonNull(college, "COLLEGE는 college 필수");
                ensureNull(central, union, department);
            }
            case DEPARTMENT -> {
                requireNonNull(college, "DEPARTMENT는 college 필수");
                requireNonNull(department, "DEPARTMENT는 department 필수");
                validateDepartmentIsBelongToCollage(college, department);
                ensureNull(central, union);
            }
        }
    }

    private static void validateDepartmentIsBelongToCollage(College college, Department department) {
        if (department.getCollege() != college) {
            throw new GeneralException(ErrorStatus._DEPARTMENT_IS_NOT_BELONG_TO_COLLEGE);
        }
    }

    private static void ensureNull(Object... objs) {
        for (Object o : objs) {
            if (o != null) {
                throw new GeneralException(ErrorStatus._INVALID_INPUT);
            }
        }
    }
}
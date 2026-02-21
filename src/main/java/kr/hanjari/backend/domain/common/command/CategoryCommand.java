package kr.hanjari.backend.domain.common.command;

import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.domain.enums.ClubType;
import kr.hanjari.backend.domain.club.domain.enums.College;
import kr.hanjari.backend.domain.club.domain.enums.Department;
import kr.hanjari.backend.domain.club.domain.enums.UnionClubCategory;

public record CategoryCommand(
        ClubType type,
        CentralClubCategory central,
        UnionClubCategory union,
        College college,
        Department department
) {
    public static CategoryCommand of(ClubType type, CentralClubCategory central, UnionClubCategory union,
                                     College college, Department department) {
        return new CategoryCommand(type, central, union, college, department);
    }

}
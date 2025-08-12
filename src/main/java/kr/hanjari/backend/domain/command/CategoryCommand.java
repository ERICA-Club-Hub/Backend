package kr.hanjari.backend.domain.command;

import kr.hanjari.backend.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.enums.ClubType;
import kr.hanjari.backend.domain.enums.College;
import kr.hanjari.backend.domain.enums.Department;
import kr.hanjari.backend.domain.enums.UnionClubCategory;

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
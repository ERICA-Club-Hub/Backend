package kr.hanjari.backend.domain.club.domain.repository.search;

import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.domain.enums.ClubType;
import kr.hanjari.backend.domain.club.domain.enums.College;
import kr.hanjari.backend.domain.club.domain.enums.Department;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.domain.enums.UnionClubCategory;

public record ClubSearchProjection(
        Long clubId,
        String name,
        String oneLiner,
        String fileKey,
        ClubType clubType,
        CentralClubCategory centralCategory,
        UnionClubCategory unionCategory,
        College college,
        Department department,
        RecruitmentStatus recruitmentStatus
) {
    public String getTag() {
        return switch (clubType) {
            case CENTRAL -> centralCategory != null ? centralCategory.toString() : "Unknown";
            case UNION -> unionCategory != null ? unionCategory.toString() : "Unknown";
            case COLLEGE -> college != null ? college.toString() : "Unknown";
            case DEPARTMENT -> department != null ? department.toString() : "Unknown";
        };
    }
}
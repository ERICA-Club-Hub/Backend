package kr.hanjari.backend.web.dto.club.request;

import kr.hanjari.backend.domain.command.CategoryCommand;
import kr.hanjari.backend.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.enums.ClubType;
import kr.hanjari.backend.domain.enums.College;
import kr.hanjari.backend.domain.enums.Department;
import kr.hanjari.backend.domain.enums.UnionClubCategory;

public record ClubBasicInformationDTO(
        String clubName,    // 동아리 이름
        String leaderEmail, // 동아리장 이메일
        ClubType clubType,  // 동아리 유형 (CENTRAL, UNION, COLLEGE, DEPARTMENT)
        CategoryDTO categoryDTO,  // 카테고리 정보 (유형별로 필요한 필드만 채움)
        String oneLiner,    // 한 줄 소개
        String briefIntroduction // 간단한 설명
) {

    public record CategoryDTO(
            CentralClubCategory central, // 중앙동아리 카테고리
            UnionClubCategory union,     // 연합동아리 카테고리
            College college,             // 단과대
            Department department        // 학과
    ) {
    }

    public void validate() {
        if (clubType == null) {
            throw new IllegalArgumentException("clubType은 필수 값입니다.");
        }
        if (categoryDTO == null) {
            throw new IllegalArgumentException("category는 필수 값입니다.");
        }

        switch (clubType) {
            case CENTRAL -> { // 중앙 동아리
                requireNonNull(categoryDTO.central(), "CENTRAL 유형에서는 centralCategory가 필수입니다.");
                requireNull(categoryDTO.union(), "CENTRAL 유형에서는 unionCategory를 지정할 수 없습니다.");
                requireNull(categoryDTO.college(), "CENTRAL 유형에서는 college를 지정할 수 없습니다.");
                requireNull(categoryDTO.department(), "CENTRAL 유형에서는 department를 지정할 수 없습니다.");
            }
            case UNION -> { // 연합 동아리
                requireNonNull(categoryDTO.union(), "UNION 유형에서는 unionCategory가 필수입니다.");
                requireNull(categoryDTO.central(), "UNION 유형에서는 centralCategory를 지정할 수 없습니다.");
                requireNull(categoryDTO.college(), "UNION 유형에서는 college를 지정할 수 없습니다.");
                requireNull(categoryDTO.department(), "UNION 유형에서는 department를 지정할 수 없습니다.");
            }
            case COLLEGE -> { // 단과대 학회 (과 정보 없음)
                requireNonNull(categoryDTO.college(), "COLLEGE 유형에서는 college가 필수입니다.");
                requireNull(categoryDTO.central(), "COLLEGE 유형에서는 centralCategory를 지정할 수 없습니다.");
                requireNull(categoryDTO.union(), "COLLEGE 유형에서는 unionCategory를 지정할 수 없습니다.");
                requireNull(categoryDTO.department(), "COLLEGE 유형에서는 department를 지정할 수 없습니다.");
            }
            case DEPARTMENT -> { // 과 학회 (과 정보 있음)
                requireNonNull(categoryDTO.college(), "DEPARTMENT 유형에서는 college가 필수입니다.");
                requireNonNull(categoryDTO.department(), "DEPARTMENT 유형에서는 department가 필수입니다.");
            }
        }
    }

    private static void requireNonNull(Object o, String m) {
        if (o == null) {
            throw new IllegalArgumentException(m);
        }
    }

    private static void requireNull(Object o, String m) {
        if (o != null) {
            throw new IllegalArgumentException(m);
        }
    }

    public CategoryCommand toCategoryCommand() {
        return new CategoryCommand(
                clubType,
                categoryDTO.central(),
                categoryDTO.union(),
                categoryDTO.college(),
                categoryDTO.department()
        );
    }

}


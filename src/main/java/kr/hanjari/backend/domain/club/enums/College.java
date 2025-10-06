package kr.hanjari.backend.domain.club.enums;

import lombok.Getter;

@Getter
public enum College {

    GLOBAL_LAW_COMMUNICATION("글로벌문화통상대학"),
    KYUNG_SANG("경상대학"),
    COMMUNICATION_CULTURE("커뮤니케이션&컬쳐대학"),
    ENGINEERING("공학대학"),
    CONVERGENCE("첨단융합대학"),
    SOFTWARE("소프트웨어융합대학"),
    DESIGN("디자인대학"),
    PHARMACY("약학대학"),
    SPORT_ARTS("예체능대학"),
    LIONS_COLLEGE("LIONS칼리지");

    private final String description;

    College(String description) {
        this.description = description;
    }

}
package kr.hanjari.backend.domain.club.domain.enums;

import lombok.Getter;

@Getter
public enum UnionClubCategory {
    IT("IT"),
    MARKETING_AD("마케팅/광고"),
    ECONOMY_MANAGEMENT("경제/경영"),
    VOLUNTEER("봉사"),
    SPORTS("스포츠"),
    LANGUAGE("언어"),
    PRESENTATION("발표"),
    BOOK("독서"),
    ETC("그 외 기타");

    private final String description;

    UnionClubCategory(String description) {
        this.description = description;
    }

}
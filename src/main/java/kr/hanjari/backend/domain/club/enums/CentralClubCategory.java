package kr.hanjari.backend.domain.club.enums;

import lombok.Getter;

@Getter
public enum CentralClubCategory {
    VOLUNTEER("봉사분과"),
    ART("예술분과"),
    SPORTS("체육분과"),
    RELIGION("종교분과"),
    ACADEMIC("학술교양분과");

    private final String description;

    CentralClubCategory(String description) {
        this.description = description;
    }

}
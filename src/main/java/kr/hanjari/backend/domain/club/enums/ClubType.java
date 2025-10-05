package kr.hanjari.backend.domain.club.enums;

import lombok.Getter;

@Getter
public enum ClubType {
    CENTRAL("중앙동아리"),
    UNION("연합동아리"),
    COLLEGE("단과대학회"),
    DEPARTMENT("과학회");

    private final String description;

    ClubType(String description) {
        this.description = description;
    }

}
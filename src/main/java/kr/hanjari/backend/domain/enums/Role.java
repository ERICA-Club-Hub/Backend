package kr.hanjari.backend.domain.enums;

import lombok.Getter;

@Getter
public enum Role {

    SERVICE_ADMIN("SERVICE_ADMIN"),
    UNION_ADMIN("UNION_ADMIN"),
    CLUB_ADMIN("CLUB_ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }


}

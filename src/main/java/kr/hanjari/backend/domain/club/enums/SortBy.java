package kr.hanjari.backend.domain.club.enums;

import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Getter
public enum SortBy {
    NAME_ASC(Sort.by(Sort.Direction.ASC, "name")),
    CATEGORY_ASC(Sort.by(Direction.ASC, "category")),
    RECRUITMENT_STATUS_ASC(Sort.by(Sort.Direction.ASC, "recruitmentStatus"));

    private final Sort sort;

    SortBy(Sort sort) {
        this.sort = sort;
    }

}

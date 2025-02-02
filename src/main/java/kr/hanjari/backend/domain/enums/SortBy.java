package kr.hanjari.backend.domain.enums;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum SortBy {
    NAME_ASC(Sort.by(Sort.Direction.ASC, "name")),
    NAME_DESC(Sort.by(Sort.Direction.DESC, "name")),
    RECRUITMENT_STATUS_ASC(Sort.by(Sort.Direction.ASC, "recruitmentStatus")
            .and(Sort.by(Sort.Direction.ASC, "name"))); // 같은 상태에서는 name 기준 오름차순 정렬

    private final Sort sort;

    SortBy(Sort sort) {
        this.sort = sort;
    }

}

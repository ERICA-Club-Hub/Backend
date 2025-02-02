package kr.hanjari.backend.repository.specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.enums.ClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import org.springframework.data.jpa.domain.Specification;

public class ClubSpecifications {

    public static Specification<Club> findByCondition(
            String name, ClubCategory clubCategory, RecruitmentStatus recruitmentStatus) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (clubCategory != null) {
                predicates.add(cb.equal(root.get("category"), clubCategory));
            }
            if (recruitmentStatus != null) {
                predicates.add(cb.equal(root.get("recruitmentStatus"), recruitmentStatus));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package kr.hanjari.backend.domain.club.domain.repository.search;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;
import org.springframework.data.jpa.domain.Specification;

public class ClubSpecifications {

    public static Specification<Club> findByCondition(
            String name, CentralClubCategory centralClubCategory, RecruitmentStatus recruitmentStatus) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (centralClubCategory != null) {
                predicates.add(cb.equal(root.get("category"), centralClubCategory));
            }
            if (recruitmentStatus != null) {
                predicates.add(cb.equal(root.get("recruitmentStatus"), recruitmentStatus));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

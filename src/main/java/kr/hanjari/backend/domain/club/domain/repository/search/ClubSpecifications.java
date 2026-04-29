package kr.hanjari.backend.domain.club.domain.repository.search;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.tag.domain.entity.ClubTag;
import org.springframework.data.jpa.domain.Specification;

public class ClubSpecifications {

    public static Specification<Club> findByCondition(
            String name, CentralClubCategory centralClubCategory, Long tagId) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (centralClubCategory != null) {
                predicates.add(cb.equal(root.get("category"), centralClubCategory));
            }
            if (tagId != null) {
                Subquery<Long> subquery = query.subquery(Long.class);
                var ctRoot = subquery.from(ClubTag.class);
                subquery.select(cb.literal(1L))
                        .where(
                                cb.equal(ctRoot.get("club").get("id"), root.get("id")),
                                cb.equal(ctRoot.get("tag").get("id"), tagId)
                        );
                predicates.add(cb.exists(subquery));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package kr.hanjari.backend.domain.tag.domain.repository;

import java.util.List;
import kr.hanjari.backend.domain.tag.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByName(String name);

    List<Tag> findAllByOrderByPriorityAsc();
}

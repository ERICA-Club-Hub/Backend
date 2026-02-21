package kr.hanjari.backend.domain.activity.domain.repository;

import java.util.List;
import java.util.Optional;
import kr.hanjari.backend.domain.activity.domain.entity.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findAllByClubId(Long clubId);

    @Query("""
            SELECT a FROM Activity a
            JOIN FETCH a.club c
            JOIN FETCH c.imageFile
            WHERE a.id = :id
            """)
    Optional<Activity> findByIdWithClubAndImageFile(@Param("id") Long id);

    @Query("""
                select distinct a
                from Activity a
                join fetch a.club c
                left join fetch c.imageFile i
                order by a.createdAt desc
            """)
    List<Activity> findRecentActivities(Pageable pageable);
}


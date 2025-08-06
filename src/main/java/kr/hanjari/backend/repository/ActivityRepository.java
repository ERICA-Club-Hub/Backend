package kr.hanjari.backend.repository;

import java.util.List;
import java.util.Optional;
import kr.hanjari.backend.domain.Activity;
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
}


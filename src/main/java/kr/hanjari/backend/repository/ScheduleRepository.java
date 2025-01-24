package kr.hanjari.backend.repository;

import java.util.List;
import kr.hanjari.backend.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.club.id = :clubId ORDER BY s.month" )
    List<Schedule> findAllByClubId(Long clubId);
}

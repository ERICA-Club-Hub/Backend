package kr.hanjari.backend.domain.club.domain.repository.detail;

import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.club.id = :clubId ORDER BY s.month" )
    List<Schedule> findAllByClubId(Long clubId);
}

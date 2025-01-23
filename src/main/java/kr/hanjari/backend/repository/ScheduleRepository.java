package kr.hanjari.backend.repository;

import java.util.List;
import kr.hanjari.backend.domain.Schedule;
import kr.hanjari.backend.domain.key.ScheduleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, ScheduleId> {

    List<Schedule> findAllByClubId(Long clubId);
}

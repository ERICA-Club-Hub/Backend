package kr.hanjari.backend.repository;

import java.util.List;
import kr.hanjari.backend.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findAllByClubId(Long clubId);
}


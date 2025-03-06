package kr.hanjari.backend.repository;

import java.util.Optional;
import kr.hanjari.backend.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    Optional<Recruitment> findByClubId(Long clubId);
}

package kr.hanjari.backend.domain.club.domain.repository.detail;

import java.util.Optional;
import kr.hanjari.backend.domain.club.domain.entity.detail.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    Optional<Recruitment> findByClubId(Long clubId);
}

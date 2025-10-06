package kr.hanjari.backend.domain.club.domain.repository.detail;

import java.util.Optional;
import kr.hanjari.backend.domain.club.domain.entity.detail.Introduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntroductionRepository extends JpaRepository<Introduction, Long> {

    Optional<Introduction> findByClubId(Long clubId);
}

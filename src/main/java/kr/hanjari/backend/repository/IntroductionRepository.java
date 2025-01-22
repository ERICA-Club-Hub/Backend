package kr.hanjari.backend.repository;

import java.util.Optional;
import kr.hanjari.backend.domain.Introduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntroductionRepository extends JpaRepository<Introduction, Long> {

    Optional<Introduction> findByClubId(Long clubId);
}

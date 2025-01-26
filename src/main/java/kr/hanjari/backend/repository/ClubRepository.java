package kr.hanjari.backend.repository;

import java.util.Optional;
import kr.hanjari.backend.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    Optional<Club> findByName(String clubName);
    Optional<Club> findByCode(String code);
    boolean existsByCode(String code);
}

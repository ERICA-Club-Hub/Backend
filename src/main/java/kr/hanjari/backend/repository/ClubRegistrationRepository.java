package kr.hanjari.backend.repository;

import kr.hanjari.backend.domain.ClubRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRegistrationRepository extends JpaRepository<ClubRegistration, Long> {
}

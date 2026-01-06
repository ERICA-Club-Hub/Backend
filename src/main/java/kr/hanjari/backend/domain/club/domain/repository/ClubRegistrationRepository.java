package kr.hanjari.backend.domain.club.domain.repository;

import kr.hanjari.backend.domain.club.domain.entity.ClubRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRegistrationRepository extends JpaRepository<ClubRegistration, Long> {

  Page<ClubRegistration> findAll(Pageable pageable);
}

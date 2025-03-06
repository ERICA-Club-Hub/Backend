package kr.hanjari.backend.repository;

import kr.hanjari.backend.domain.ServiceAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceAnnouncementRepository extends JpaRepository<ServiceAnnouncement, Long> {
}

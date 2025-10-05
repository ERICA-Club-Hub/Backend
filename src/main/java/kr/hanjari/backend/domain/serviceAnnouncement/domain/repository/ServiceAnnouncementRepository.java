package kr.hanjari.backend.domain.serviceAnnouncement.domain.repository;

import kr.hanjari.backend.domain.serviceAnnouncement.domain.entity.ServiceAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceAnnouncementRepository extends JpaRepository<ServiceAnnouncement, Long> {
}

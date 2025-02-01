package kr.hanjari.backend.repository;

import kr.hanjari.backend.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}

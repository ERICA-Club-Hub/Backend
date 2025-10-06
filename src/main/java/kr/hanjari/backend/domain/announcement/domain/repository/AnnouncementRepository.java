package kr.hanjari.backend.domain.announcement.domain.repository;

import kr.hanjari.backend.domain.announcement.domain.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}

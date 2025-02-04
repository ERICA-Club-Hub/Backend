package kr.hanjari.backend.repository;

import kr.hanjari.backend.domain.key.ActivityImageId;
import kr.hanjari.backend.domain.mapping.ActivityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityImageRepository extends JpaRepository<ActivityImage, ActivityImageId> {
}

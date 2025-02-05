package kr.hanjari.backend.repository;

import kr.hanjari.backend.domain.key.ActivityImageId;
import kr.hanjari.backend.domain.mapping.ActivityImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityImageRepository extends JpaRepository<ActivityImage, ActivityImageId> {
    List<ActivityImage> findAllByActivityId(Long activityId);
    List<ActivityImage> findAllByActivityIdOrderByOrderIndexAsc(Long activityId);
    Optional<ActivityImage> findFirstByActivityIdOrderByIdAsc(Long activityId);
}

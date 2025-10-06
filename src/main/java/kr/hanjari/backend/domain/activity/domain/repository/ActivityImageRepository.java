package kr.hanjari.backend.domain.activity.domain.repository;

import kr.hanjari.backend.domain.activity.domain.entity.ActivityImageId;
import kr.hanjari.backend.domain.activity.domain.entity.ActivityImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityImageRepository extends JpaRepository<ActivityImage, ActivityImageId> {
    List<ActivityImage> findAllByActivityId(Long activityId);
    List<ActivityImage> findAllByActivityIdOrderByOrderIndexAsc(Long activityId);
    Optional<ActivityImage> findFirstByActivityIdOrderByIdAsc(Long activityId);
}

package kr.hanjari.backend.domain.activity.domain.repository;

import kr.hanjari.backend.domain.activity.domain.entity.ActivityImageId;
import kr.hanjari.backend.domain.activity.domain.entity.ActivityImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActivityImageRepository extends JpaRepository<ActivityImage, ActivityImageId> {
    List<ActivityImage> findAllByActivityId(Long activityId);
    List<ActivityImage> findAllByActivityIdOrderByOrderIndexAsc(Long activityId);
    Optional<ActivityImage> findFirstByActivityIdOrderByIdAsc(Long activityId);

    void deleteAllByActivityId(Long activityId);
    void deleteByImageFileId(Long imageFileId);

    @Query("SELECT ai.imageFile.id FROM ActivityImage ai where ai.activity.id = :activityId")
    List<Long> findAllFileIdsByActivityId(Long activityId);
}

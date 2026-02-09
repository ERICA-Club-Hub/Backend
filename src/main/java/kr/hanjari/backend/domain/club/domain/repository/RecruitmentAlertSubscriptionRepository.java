package kr.hanjari.backend.domain.club.domain.repository;

import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.RecruitmentAlertSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentAlertSubscriptionRepository extends JpaRepository<RecruitmentAlertSubscription, Long> {

    boolean existsByClub_IdAndEmail(Long clubId, String email);

    List<RecruitmentAlertSubscription> findAllByClub_Id(Long clubId);
}

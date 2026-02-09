package kr.hanjari.backend.domain.club.application.command;

import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.RecruitmentAlertSubscription;
import kr.hanjari.backend.domain.club.domain.repository.RecruitmentAlertSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentAlertSubscriptionCommandService {

    private final RecruitmentAlertSubscriptionRepository recruitmentAlertSubscriptionRepository;

    public void deleteAllInBatch(List<RecruitmentAlertSubscription> subscriptions) {
        if (subscriptions.isEmpty()) {
            return;
        }
        recruitmentAlertSubscriptionRepository.deleteAllInBatch(subscriptions);
    }
}

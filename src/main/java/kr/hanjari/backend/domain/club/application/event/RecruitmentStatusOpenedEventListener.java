package kr.hanjari.backend.domain.club.application.event;

import java.util.ArrayList;
import java.util.List;
import kr.hanjari.backend.domain.club.application.command.impl.RecruitmentAlertSubscriptionCommandService;
import kr.hanjari.backend.domain.club.application.query.MailSender;
import kr.hanjari.backend.domain.club.domain.entity.RecruitmentAlertSubscription;
import kr.hanjari.backend.domain.club.domain.repository.RecruitmentAlertSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecruitmentStatusOpenedEventListener {

    @Value("${club.detail.base-url:https://develop.hanjari.site/club}")
    private String clubDetailBaseUrl;

    private final RecruitmentAlertSubscriptionRepository recruitmentAlertSubscriptionRepository;
    private final RecruitmentAlertSubscriptionCommandService recruitmentAlertSubscriptionCommandService;
    private final MailSender mailSender;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void handle(RecruitmentStatusOpenedEvent event) {
        List<RecruitmentAlertSubscription> subscriptions =
                recruitmentAlertSubscriptionRepository.findAllByClub_Id(event.clubId());

        if (subscriptions.isEmpty()) {
            return;
        }

        String clubDetailUrl = buildClubDetailUrl(event.clubId());
        List<RecruitmentAlertSubscription> successSubscriptions = new ArrayList<>();

        for (RecruitmentAlertSubscription subscription : subscriptions) {
            try {
                mailSender.sendRecruitmentOpenEmail(subscription.getEmail(), event.clubName(), clubDetailUrl);
                successSubscriptions.add(subscription);
            } catch (Exception e) {
                log.warn("Failed to send recruitment open email. clubId={}, email={}",
                        event.clubId(), subscription.getEmail(), e);
            }
        }

        recruitmentAlertSubscriptionCommandService.deleteAllInBatch(successSubscriptions);
    }

    private String buildClubDetailUrl(Long clubId) {
        if (clubDetailBaseUrl.endsWith("/")) {
            return clubDetailBaseUrl + clubId;
        }
        return clubDetailBaseUrl + "/" + clubId;
    }
}

package kr.hanjari.backend.domain.club.application.event;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.hanjari.backend.domain.club.application.command.RecruitmentAlertSubscriptionCommandService;
import kr.hanjari.backend.domain.club.application.query.MailSender;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.RecruitmentAlertSubscription;
import kr.hanjari.backend.domain.club.domain.repository.RecruitmentAlertSubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class RecruitmentStatusOpenedEventListenerTest {

    @InjectMocks
    private RecruitmentStatusOpenedEventListener listener;

    @Mock
    private RecruitmentAlertSubscriptionRepository recruitmentAlertSubscriptionRepository;
    @Mock
    private RecruitmentAlertSubscriptionCommandService recruitmentAlertSubscriptionCommandService;
    @Mock
    private MailSender mailSender;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(listener, "clubDetailBaseUrl", "https://develop.hanjari.site/club");
    }

    @Test
    @DisplayName("구독자 전체에게 메일 전송 성공 시 성공 구독 목록을 삭제 처리한다")
    void should_send_mail_and_delete_subscription_on_success() {
        Long clubId = 171L;
        RecruitmentAlertSubscription first = subscription(clubId, "first@example.com");
        RecruitmentAlertSubscription second = subscription(clubId, "second@example.com");
        when(recruitmentAlertSubscriptionRepository.findAllByClub_Id(clubId))
                .thenReturn(List.of(first, second));

        listener.handle(new RecruitmentStatusOpenedEvent(clubId, "한자리"));

        verify(mailSender, times(1))
                .sendRecruitmentOpenEmail("first@example.com", "한자리", "https://develop.hanjari.site/club/171");
        verify(mailSender, times(1))
                .sendRecruitmentOpenEmail("second@example.com", "한자리", "https://develop.hanjari.site/club/171");
        verify(recruitmentAlertSubscriptionCommandService, times(1))
                .deleteAllInBatch(List.of(first, second));
    }

    @Test
    @DisplayName("메일 전송 실패 시 실패 건을 제외하고 성공 건만 삭제 처리한다")
    void should_log_and_continue_when_mail_sending_fails() {
        Long clubId = 171L;
        RecruitmentAlertSubscription first = subscription(clubId, "fail@example.com");
        RecruitmentAlertSubscription second = subscription(clubId, "ok@example.com");
        when(recruitmentAlertSubscriptionRepository.findAllByClub_Id(clubId))
                .thenReturn(List.of(first, second));

        doThrow(new RuntimeException("mail failure"))
                .when(mailSender)
                .sendRecruitmentOpenEmail(eq("fail@example.com"), anyString(), anyString());

        listener.handle(new RecruitmentStatusOpenedEvent(clubId, "한자리"));

        verify(recruitmentAlertSubscriptionCommandService, times(1))
                .deleteAllInBatch(List.of(second));
    }

    private RecruitmentAlertSubscription subscription(Long clubId, String email) {
        Club club = Club.builder()
                .id(clubId)
                .name("한자리")
                .leaderEmail("leader@example.com")
                .oneLiner("one")
                .briefIntroduction("brief")
                .build();
        return RecruitmentAlertSubscription.create(club, email);
    }
}

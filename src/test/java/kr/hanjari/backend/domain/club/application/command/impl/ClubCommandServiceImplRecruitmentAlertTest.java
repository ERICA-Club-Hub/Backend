package kr.hanjari.backend.domain.club.application.command.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.hanjari.backend.domain.club.application.command.CodeGenerator;
import kr.hanjari.backend.domain.club.application.event.RecruitmentStatusOpenedEvent;
import kr.hanjari.backend.domain.club.application.query.MailSender;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.RecruitmentAlertSubscription;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.domain.repository.ClubRegistrationRepository;
import kr.hanjari.backend.domain.club.domain.repository.ClubRepository;
import kr.hanjari.backend.domain.club.domain.repository.RecruitmentAlertSubscriptionRepository;
import kr.hanjari.backend.domain.club.domain.repository.detail.IntroductionRepository;
import kr.hanjari.backend.domain.club.domain.repository.detail.RecruitmentRepository;
import kr.hanjari.backend.domain.club.domain.repository.detail.ScheduleRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.ClubDetailDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.IntroductionDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.RecruitmentDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.ScheduleDescriptionDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.ScheduleDraftRepository;
import kr.hanjari.backend.domain.file.application.FileService;
import kr.hanjari.backend.domain.file.domain.repository.FileRepository;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.infrastructure.slack.SlackWebhookSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class ClubCommandServiceImplRecruitmentAlertTest {

    @InjectMocks
    private ClubCommandServiceImpl clubCommandService;

    @Mock
    private FileRepository fileRepository;
    @Mock
    private ClubRepository clubRepository;
    @Mock
    private RecruitmentAlertSubscriptionRepository recruitmentAlertSubscriptionRepository;
    @Mock
    private ClubRegistrationRepository clubRegistrationRepository;
    @Mock
    private IntroductionRepository introductionRepository;
    @Mock
    private RecruitmentRepository recruitmentRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private IntroductionDraftRepository introductionDraftRepository;
    @Mock
    private RecruitmentDraftRepository recruitmentDraftRepository;
    @Mock
    private ClubDetailDraftRepository clubDetailDraftRepository;
    @Mock
    private ScheduleDraftRepository scheduleDraftRepository;
    @Mock
    private ScheduleDescriptionDraftRepository scheduleDescriptionDraftRepository;
    @Mock
    private CodeGenerator codeGenerator;
    @Mock
    private MailSender mailSender;
    @Mock
    private FileService fileService;
    @Mock
    private SlackWebhookSender slackWebhookSender;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    @DisplayName("알림 이메일을 정상 등록한다")
    void should_subscribe_recruitment_alert() {
        Long clubId = 171L;
        Club club = club(clubId, "한자리", RecruitmentStatus.UPCOMING);
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));
        when(recruitmentAlertSubscriptionRepository.existsByClub_IdAndEmail(clubId, "student@example.com"))
                .thenReturn(false);

        clubCommandService.subscribeRecruitmentAlert(clubId, "Student@Example.com ");

        verify(recruitmentAlertSubscriptionRepository)
                .save(any(RecruitmentAlertSubscription.class));
    }

    @Test
    @DisplayName("중복 이메일은 멱등하게 무시한다")
    void should_ignore_duplicate_email_subscription() {
        Long clubId = 171L;
        Club club = club(clubId, "한자리", RecruitmentStatus.UPCOMING);
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));
        when(recruitmentAlertSubscriptionRepository.existsByClub_IdAndEmail(clubId, "student@example.com"))
                .thenReturn(true);

        clubCommandService.subscribeRecruitmentAlert(clubId, "student@example.com");

        verify(recruitmentAlertSubscriptionRepository, never()).save(any());
    }

    @Test
    @DisplayName("이메일 형식이 잘못되면 예외를 던진다")
    void should_throw_when_email_format_is_invalid() {
        Long clubId = 171L;
        Club club = club(clubId, "한자리", RecruitmentStatus.UPCOMING);
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));

        GeneralException exception = assertThrows(
                GeneralException.class,
                () -> clubCommandService.subscribeRecruitmentAlert(clubId, "invalid-email")
        );

        assertEquals(ErrorStatus._INVALID_INPUT, exception.getCode());
        verify(recruitmentAlertSubscriptionRepository, never()).save(any());
    }

    @Test
    @DisplayName("모집상태가 UPCOMING에서 OPEN으로 바뀌면 이벤트를 발행한다")
    void should_publish_event_when_recruitment_changes_upcoming_to_open() {
        Long clubId = 171L;
        Club club = club(clubId, "한자리", RecruitmentStatus.UPCOMING);
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));

        clubCommandService.updateClubRecruitmentStatus(clubId, 1);

        ArgumentCaptor<RecruitmentStatusOpenedEvent> captor =
                ArgumentCaptor.forClass(RecruitmentStatusOpenedEvent.class);
        verify(applicationEventPublisher).publishEvent(captor.capture());
        RecruitmentStatusOpenedEvent event = captor.getValue();
        assertEquals(clubId, event.clubId());
        assertEquals("한자리", event.clubName());
        verify(clubRepository).save(club);
    }

    @Test
    @DisplayName("모집상태가 CLOSED에서 OPEN으로 바뀌면 이벤트를 발행한다")
    void should_publish_event_when_recruitment_changes_closed_to_open() {
        Long clubId = 171L;
        Club club = club(clubId, "한자리", RecruitmentStatus.CLOSED);
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));

        clubCommandService.updateClubRecruitmentStatus(clubId, 1);

        ArgumentCaptor<RecruitmentStatusOpenedEvent> captor =
            ArgumentCaptor.forClass(RecruitmentStatusOpenedEvent.class);
        verify(applicationEventPublisher).publishEvent(captor.capture());
        RecruitmentStatusOpenedEvent event = captor.getValue();
        assertEquals(clubId, event.clubId());
        assertEquals("한자리", event.clubName());
        verify(clubRepository).save(club);
    }

    @Test
    @DisplayName("모집상태가 다른 전이면 이벤트를 발행하지 않는다")
    void should_not_publish_event_when_status_transition_is_not_upcoming_to_open() {
        Long clubId = 171L;
        Club club = club(clubId, "한자리", RecruitmentStatus.OPEN);
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));

        clubCommandService.updateClubRecruitmentStatus(clubId, 2);

        verify(applicationEventPublisher, never()).publishEvent(any());
        verify(clubRepository).save(eq(club));
    }

    private Club club(Long id, String name, RecruitmentStatus status) {
        return Club.builder()
                .id(id)
                .name(name)
                .leaderEmail("leader@example.com")
                .oneLiner("one")
                .briefIntroduction("brief")
                .recruitmentStatus(status)
                .build();
    }
}

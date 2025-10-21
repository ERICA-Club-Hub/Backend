package kr.hanjari.backend.domain.club.application.query.impl;

import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createClubPage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import kr.hanjari.backend.domain.club.application.command.ClubCommandService;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.ClubRegistration;
import kr.hanjari.backend.domain.club.domain.entity.detail.Introduction;
import kr.hanjari.backend.domain.club.domain.entity.detail.Recruitment;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;
import kr.hanjari.backend.domain.club.domain.entity.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.IntroductionDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.RecruitmentDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.ScheduleDraft;
import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.domain.enums.College;
import kr.hanjari.backend.domain.club.domain.enums.Department;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.domain.enums.SortBy;
import kr.hanjari.backend.domain.club.domain.enums.UnionClubCategory;
import kr.hanjari.backend.domain.club.domain.repository.ClubRegistrationRepository;
import kr.hanjari.backend.domain.club.domain.repository.ClubRepository;
import kr.hanjari.backend.domain.club.domain.repository.detail.IntroductionRepository;
import kr.hanjari.backend.domain.club.domain.repository.detail.RecruitmentRepository;
import kr.hanjari.backend.domain.club.domain.repository.detail.ScheduleRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.ClubDetailDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.IntroductionDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.RecruitmentDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.ScheduleDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.search.ClubSearchRepository;
import kr.hanjari.backend.domain.club.fixtures.ClubTestFixture;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubIntroductionResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubOverviewResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubRecruitmentResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubScheduleResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubSearchResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.GetRegistrationsResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubBasicInfoResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubDetailDraftResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubIntroductionDraftResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubRecruitmentDraftResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubScheduleDraftResponse;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.infrastructure.s3.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

@ExtendWith(MockitoExtension.class)
class ClubQueryServiceImplTest {

    @InjectMocks
    private ClubQueryServiceImpl clubQueryService;

    @Mock
    private ClubRepository clubRepository;
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
    private ClubCommandService clubCommandService;
    @Mock
    private S3Service s3Service;
    @Mock
    private ClubSearchRepository clubSearchRepository;

    private Club club;
    private ClubRegistration clubRegistration;
    private Introduction introduction;
    private Recruitment recruitment;
    private Schedule schedule;
    private ClubDetailDraft clubDetailDraft;
    private IntroductionDraft introductionDraft;
    private RecruitmentDraft recruitmentDraft;
    private ScheduleDraft scheduleDraft;

    @BeforeEach
    void setUp() {
        club = ClubTestFixture.createClub();
        clubRegistration = ClubTestFixture.createClubRegistration();
        introduction = ClubTestFixture.createIntroduction(club);
        recruitment = ClubTestFixture.createRecruitment(club);
        schedule = ClubTestFixture.createSchedule(club);
        clubDetailDraft = ClubTestFixture.createClubDetailDraft(club.getId());
        introductionDraft = ClubTestFixture.createIntroductionDraft(club);
        recruitmentDraft = ClubTestFixture.createRecruitmentDraft(club);
        scheduleDraft = ClubTestFixture.createScheduleDraft(club);

    }

    @Test
    @DisplayName("동아리 등록 목록을 성공적으로 조회한다")
    void should_retrieve_club_registrations_successfully() {
        // Given
        when(clubRegistrationRepository.findAll()).thenReturn(List.of(clubRegistration));

        // When
        GetRegistrationsResponse response = clubQueryService.getRegistrations();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.clubRegistrationResponseDTOList()).hasSize(1);
        assertThat(response.clubRegistrationResponseDTOList().getFirst().clubName()).isEqualTo(
                clubRegistration.getName());
        verify(clubRegistrationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("등록된 동아리가 없을 경우 빈 목록을 반환한다")
    void should_return_empty_list_when_no_registrations_exist() {
        // Given
        when(clubRegistrationRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        GetRegistrationsResponse response = clubQueryService.getRegistrations();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.clubRegistrationResponseDTOList()).isEmpty();
        verify(clubRegistrationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("동아리 상세 정보를 성공적으로 조회한다")
    void should_retrieve_club_detail_successfully() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(club));
        when(s3Service.getDownloadUrl(anyLong())).thenReturn("http://s3.test.com/image.jpg");
        doNothing().when(clubCommandService).incrementClubViewCount(anyLong());

        // When
        ClubResponse response = clubQueryService.findClubDetail(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo(club.getName());
        verify(clubRepository, times(1)).findById(club.getId());
        verify(clubCommandService, times(1)).incrementClubViewCount(club.getId());
        verify(s3Service, times(1)).getDownloadUrl(anyLong());
    }

    @Test
    @DisplayName("존재하지 않는 동아리 ID로 상세 조회 시 GeneralException이 발생한다")
    void should_throw_exception_when_finding_club_detail_with_non_existent_id() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralException.class, () -> clubQueryService.findClubDetail(club.getId()));
        verify(clubRepository, times(1)).findById(club.getId());
        verify(clubCommandService, times(0)).incrementClubViewCount(anyLong());
        verify(s3Service, times(0)).getDownloadUrl(anyLong());
    }

    @Test
    @DisplayName("동아리 개요 정보를 성공적으로 조회한다")
    void should_retrieve_club_overview_successfully() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(club));
        when(s3Service.getDownloadUrl(anyLong())).thenReturn("http://s3.test.com/image.jpg");

        // When
        ClubOverviewResponse response = clubQueryService.findClubOverview(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo(club.getName());
        assertThat(response.description()).isEqualTo(club.getOneLiner());
        verify(clubRepository, times(1)).findById(club.getId());
        verify(s3Service, times(1)).getDownloadUrl(anyLong());
    }

    @Test
    @DisplayName("존재하지 않는 동아리 ID로 개요 조회 시 GeneralException이 발생한다")
    void should_throw_exception_when_finding_club_overview_with_non_existent_id() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralException.class, () -> clubQueryService.findClubOverview(club.getId()));
        verify(clubRepository, times(1)).findById(club.getId());
        verify(s3Service, times(0)).getDownloadUrl(anyLong());
    }

    @Test
    @DisplayName("동아리 기본 정보를 성공적으로 조회한다")
    void should_retrieve_club_basic_info_successfully() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(club));

        // When
        ClubBasicInfoResponse response = clubQueryService.findClubBasicInfo(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.leaderName()).isEqualTo(club.getLeaderName());
        assertThat(response.leaderEmail()).isEqualTo(club.getLeaderEmail());
        verify(clubRepository, times(1)).findById(club.getId());
    }

    @Test
    @DisplayName("존재하지 않는 동아리 ID로 기본 정보 조회 시 GeneralException이 발생한다")
    void should_throw_exception_when_finding_club_basic_info_with_non_existent_id() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralException.class, () -> clubQueryService.findClubBasicInfo(club.getId()));
        verify(clubRepository, times(1)).findById(club.getId());
    }

    @Test
    @DisplayName("동아리 상세 초안 정보를 성공적으로 조회한다")
    void should_retrieve_club_detail_draft_successfully() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(club));
        when(clubDetailDraftRepository.findById(anyLong())).thenReturn(Optional.of(clubDetailDraft));

        // When
        ClubDetailDraftResponse response = clubQueryService.findClubDetailDraft(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo(club.getName());
        verify(clubRepository, times(1)).findById(club.getId());
        verify(clubDetailDraftRepository, times(1)).findById(club.getId());
        verify(s3Service, times(1)).getDownloadUrl(anyLong());
    }

    @Test
    @DisplayName("존재하지 않는 동아리 ID로 상세 초안 조회 시 GeneralException이 발생한다")
    void should_throw_exception_when_finding_club_detail_draft_with_non_existent_id() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralException.class, () -> clubQueryService.findClubDetailDraft(club.getId()));
        verify(clubRepository, times(1)).findById(club.getId());
        verify(clubDetailDraftRepository, times(0)).findById(anyLong());
    }

    @Test
    @DisplayName("동아리 상세 초안이 존재하지 않을 경우 GeneralException이 발생한다")
    void should_throw_exception_when_club_detail_draft_not_found() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(club));
        when(clubDetailDraftRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralException.class, () -> clubQueryService.findClubDetailDraft(club.getId()));
        verify(clubRepository, times(1)).findById(club.getId());
        verify(clubDetailDraftRepository, times(1)).findById(club.getId());
    }

    @Test
    @DisplayName("동아리 활동 일정을 모두 성공적으로 조회한다")
    void should_retrieve_all_club_activities_successfully() {
        // Given
        when(clubRepository.existsById(anyLong())).thenReturn(true);
        when(scheduleRepository.findAllByClubId(anyLong())).thenReturn(List.of(schedule));

        // When
        ClubScheduleResponse response = clubQueryService.findAllClubActivities(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.schedules()).hasSize(1);
        assertThat(response.schedules().getFirst().content()).isEqualTo(schedule.getContent());
        verify(clubRepository, times(1)).existsById(club.getId());
        verify(scheduleRepository, times(1)).findAllByClubId(club.getId());
    }

    @Test
    @DisplayName("존재하지 않는 동아리 ID로 활동 일정 조회 시 GeneralException이 발생한다")
    void should_throw_exception_when_finding_all_club_activities_with_non_existent_id() {
        // Given
        when(clubRepository.existsById(anyLong())).thenReturn(false);

        // When & Then
        assertThrows(GeneralException.class, () -> clubQueryService.findAllClubActivities(club.getId()));
        verify(clubRepository, times(1)).existsById(club.getId());
        verify(scheduleRepository, times(0)).findAllByClubId(anyLong());
    }

    @Test
    @DisplayName("동아리 활동 일정 초안을 모두 성공적으로 조회한다")
    void should_retrieve_all_club_activities_draft_successfully() {
        // Given
        when(clubRepository.existsById(anyLong())).thenReturn(true);
        when(scheduleDraftRepository.findAllByClubIdOrderByMonth(anyLong())).thenReturn(List.of(scheduleDraft));

        // When
        ClubScheduleDraftResponse response = clubQueryService.findAllClubActivitiesDraft(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.schedules()).hasSize(1);
        assertThat(response.schedules().getFirst().content()).isEqualTo(scheduleDraft.getContent());
        verify(clubRepository, times(1)).existsById(club.getId());
        verify(scheduleDraftRepository, times(1)).findAllByClubIdOrderByMonth(club.getId());
    }

    @Test
    @DisplayName("존재하지 않는 동아리 ID로 활동 일정 초안 조회 시 GeneralException이 발생한다")
    void should_throw_exception_when_finding_all_club_activities_draft_with_non_existent_id() {
        // Given
        when(clubRepository.existsById(anyLong())).thenReturn(false);

        // When & Then
        assertThrows(GeneralException.class, () -> clubQueryService.findAllClubActivitiesDraft(club.getId()));
        verify(clubRepository, times(1)).existsById(club.getId());
        verify(scheduleDraftRepository, times(0)).findAllByClubIdOrderByMonth(anyLong());
    }

    @Test
    @DisplayName("동아리 소개 정보를 성공적으로 조회한다")
    void should_retrieve_club_introduction_successfully() {
        // Given
        when(clubRepository.existsById(anyLong())).thenReturn(true);
        when(introductionRepository.findByClubId(anyLong())).thenReturn(Optional.of(introduction));

        // When
        ClubIntroductionResponse response = clubQueryService.findClubIntroduction(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.introduction()).isEqualTo(introduction.getContent1());
        verify(clubRepository, times(1)).existsById(club.getId());
        verify(introductionRepository, times(1)).findByClubId(club.getId());
    }

    @Test
    @DisplayName("동아리 소개 정보가 없을 경우 null을 반환한다")
    void should_return_null_when_club_introduction_not_found() {
        // Given
        when(clubRepository.existsById(anyLong())).thenReturn(true);
        when(introductionRepository.findByClubId(anyLong())).thenReturn(Optional.empty());

        // When
        ClubIntroductionResponse response = clubQueryService.findClubIntroduction(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.introduction()).isNull();
        verify(clubRepository, times(1)).existsById(club.getId());
        verify(introductionRepository, times(1)).findByClubId(club.getId());
    }

    @Test
    @DisplayName("존재하지 않는 동아리 ID로 소개 정보 조회 시 GeneralException이 발생한다")
    void should_throw_exception_when_finding_club_introduction_with_non_existent_id() {
        // Given
        when(clubRepository.existsById(anyLong())).thenReturn(false);

        // When & Then
        assertThrows(GeneralException.class, () -> clubQueryService.findClubIntroduction(club.getId()));
        verify(clubRepository, times(1)).existsById(club.getId());
        verify(introductionRepository, times(0)).findByClubId(anyLong());
    }

    @Test
    @DisplayName("동아리 소개 초안 정보를 성공적으로 조회한다")
    void should_retrieve_club_introduction_draft_successfully() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(club));
        when(introductionDraftRepository.findByClubId(anyLong())).thenReturn(Optional.of(introductionDraft));

        // When
        ClubIntroductionDraftResponse response = clubQueryService.findClubIntroductionDraft(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.introduction()).isEqualTo(introductionDraft.getContent1());
        assertThat(response.club().name()).isEqualTo(club.getName());
        verify(clubRepository, times(1)).findById(club.getId());
        verify(introductionDraftRepository, times(1)).findByClubId(club.getId());
        verify(s3Service, times(1)).getDownloadUrl(anyLong());
    }

    @Test
    @DisplayName("존재하지 않는 동아리 ID로 소개 초안 조회 시 GeneralException이 발생한다")
    void should_throw_exception_when_finding_club_introduction_draft_with_non_existent_id() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralException.class, () -> clubQueryService.findClubIntroductionDraft(club.getId()));
        verify(clubRepository, times(1)).findById(club.getId());
        verify(introductionDraftRepository, times(0)).findByClubId(anyLong());
    }

    @Test
    @DisplayName("동아리 소개 초안이 존재하지 않을 경우 GeneralException이 발생한다")
    void should_throw_exception_when_club_introduction_draft_not_found() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(club));
        when(introductionDraftRepository.findByClubId(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralException.class, () -> clubQueryService.findClubIntroductionDraft(club.getId()));
        verify(clubRepository, times(1)).findById(club.getId());
        verify(introductionDraftRepository, times(1)).findByClubId(club.getId());
    }

    @Test
    @DisplayName("동아리 모집 정보를 소개 정보와 함께 성공적으로 조회한다")
    void should_retrieve_club_recruitment_with_introduction_successfully() {
        // Given
        when(recruitmentRepository.findByClubId(anyLong())).thenReturn(Optional.of(recruitment));
        when(introductionRepository.findByClubId(anyLong())).thenReturn(Optional.of(introduction));

        // When
        ClubRecruitmentResponse response = clubQueryService.findClubRecruitment(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.due()).isEqualTo(recruitment.getContent1());
        assertThat(response.target()).isEqualTo(introduction.getContent2());
        verify(recruitmentRepository, times(1)).findByClubId(club.getId());
        verify(introductionRepository, times(1)).findByClubId(club.getId());
    }

    @Test
    @DisplayName("동아리 모집 정보를 소개 정보 없이 성공적으로 조회한다")
    void should_retrieve_club_recruitment_without_introduction_successfully() {
        // Given
        when(recruitmentRepository.findByClubId(anyLong())).thenReturn(Optional.of(recruitment));
        when(introductionRepository.findByClubId(anyLong())).thenReturn(Optional.empty());

        // When
        ClubRecruitmentResponse response = clubQueryService.findClubRecruitment(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.due()).isEqualTo(recruitment.getContent1());
        assertThat(response.target()).isNull();
        verify(recruitmentRepository, times(1)).findByClubId(club.getId());
        verify(introductionRepository, times(1)).findByClubId(club.getId());
    }

    @Test
    @DisplayName("동아리 모집 정보가 없을 경우 null을 반환한다")
    void should_return_null_when_club_recruitment_not_found() {
        // Given
        when(recruitmentRepository.findByClubId(anyLong())).thenReturn(Optional.empty());
        when(introductionRepository.findByClubId(anyLong())).thenReturn(Optional.of(introduction));

        // When
        ClubRecruitmentResponse response = clubQueryService.findClubRecruitment(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.due()).isNull();
        assertThat(response.target()).isNull(); // target should also be null if recruitment is null
        verify(recruitmentRepository, times(1)).findByClubId(club.getId());
        verify(introductionRepository, times(1)).findByClubId(club.getId());
    }

    @Test
    @DisplayName("동아리 모집 초안 정보를 성공적으로 조회한다")
    void should_retrieve_club_recruitment_draft_successfully() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(club));
        when(recruitmentDraftRepository.findByClubId(anyLong())).thenReturn(Optional.of(recruitmentDraft));

        // When
        ClubRecruitmentDraftResponse response = clubQueryService.findClubRecruitmentDraft(club.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.due()).isEqualTo(recruitmentDraft.getContent1());
        assertThat(response.club().name()).isEqualTo(club.getName());
        verify(clubRepository, times(1)).findById(club.getId());
        verify(recruitmentDraftRepository, times(1)).findByClubId(club.getId());
        verify(s3Service, times(1)).getDownloadUrl(anyLong());
    }

    @Test
    @DisplayName("존재하지 않는 동아리 ID로 모집 초안 조회 시 GeneralException이 발생한다")
    void should_throw_exception_when_finding_club_recruitment_draft_with_non_existent_id() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralException.class, () -> clubQueryService.findClubRecruitmentDraft(club.getId()));
        verify(clubRepository, times(1)).findById(club.getId());
        verify(recruitmentDraftRepository, times(0)).findByClubId(anyLong());
    }

    @Test
    @DisplayName("동아리 모집 초안이 존재하지 않을 경우 GeneralException이 발생한다")
    void should_throw_exception_when_club_recruitment_draft_not_found() {
        // Given
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(club));
        when(recruitmentDraftRepository.findByClubId(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralException.class, () -> clubQueryService.findClubRecruitmentDraft(club.getId()));
        verify(clubRepository, times(1)).findById(club.getId());
        verify(recruitmentDraftRepository, times(1)).findByClubId(club.getId());
    }

    @Test
    @DisplayName("중앙 동아리를 조건으로 성공적으로 검색한다")
    void should_find_central_clubs_by_condition_successfully() {
        // Given
        Page<Club> clubPage = createClubPage();
        when(clubSearchRepository.findCentralClubsByCondition(
                anyString(), any(RecruitmentStatus.class), any(SortBy.class),
                any(CentralClubCategory.class), anyInt(), anyInt()))
                .thenReturn(clubPage);

        // When
        ClubSearchResponse response = clubQueryService.findCentralClubsByCondition(
                "keyword", RecruitmentStatus.OPEN, SortBy.NAME_ASC, CentralClubCategory.ACADEMIC, 0, 1);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.content()).hasSize(1);
        assertThat(response.content().getFirst().name()).isEqualTo(club.getName());
        verify(clubSearchRepository, times(1)).findCentralClubsByCondition(
                anyString(), any(), any(),
                any(), anyInt(), anyInt());
        verify(s3Service, times(1)).getDownloadUrl(anyLong());
    }

    @Test
    @DisplayName("연합 동아리를 조건으로 성공적으로 검색한다")
    void should_find_union_clubs_by_condition_successfully() {
        // Given
        when(clubSearchRepository.findUnionClubsByCondition(
                anyString(), any(), any(),
                any(), anyInt(), anyInt()))
                .thenReturn(createClubPage());

        // When
        ClubSearchResponse response = clubQueryService.findUnionClubsByCondition(
                "keyword", RecruitmentStatus.OPEN, SortBy.NAME_ASC, UnionClubCategory.IT, 0, 1);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.content()).hasSize(1);
        assertThat(response.content().getFirst().name()).isEqualTo(club.getName());
        verify(clubSearchRepository, times(1)).findUnionClubsByCondition(
                anyString(), any(), any(),
                any(), anyInt(), anyInt());
        verify(s3Service, times(1)).getDownloadUrl(anyLong());
    }

    @Test
    @DisplayName("단과대 동아리를 조건으로 성공적으로 검색한다")
    void should_find_college_clubs_by_condition_successfully() {
        // Given
        when(clubSearchRepository.findCollegeClubsByCondition(
                anyString(), any(), any(),
                any(), anyInt(), anyInt()))
                .thenReturn(createClubPage());

        // When
        ClubSearchResponse response = clubQueryService.findCollegeClubsByCondition(
                "keyword", RecruitmentStatus.OPEN, SortBy.NAME_ASC, College.ENGINEERING, 0, 1);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.content()).hasSize(1);
        assertThat(response.content().getFirst().name()).isEqualTo(club.getName());
        verify(clubSearchRepository, times(1)).findCollegeClubsByCondition(
                anyString(), any(), any(),
                any(), anyInt(), anyInt());
        verify(s3Service, times(1)).getDownloadUrl(anyLong());
    }

    @Test
    @DisplayName("학과 동아리를 조건으로 성공적으로 검색한다")
    void should_find_department_clubs_by_condition_successfully() {
        // Given
        when(clubSearchRepository.findDepartmentClubsByCondition(
                anyString(), any(), any(),
                any(), any(), anyInt(), anyInt()))
                .thenReturn(createClubPage());

        // When
        ClubSearchResponse response = clubQueryService.findDepartmentClubsByCondition(
                "keyword", RecruitmentStatus.OPEN, SortBy.NAME_ASC, College.ENGINEERING,
                Department.COMPUTER, 0, 1);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.content()).hasSize(1);
        assertThat(response.content().getFirst().name()).isEqualTo(club.getName());
        verify(clubSearchRepository, times(1)).findDepartmentClubsByCondition(
                anyString(), any(), any(),
                any(), any(), anyInt(), anyInt());
        verify(s3Service, times(1)).getDownloadUrl(anyLong());
    }

    @Test
    @DisplayName("인기 동아리를 성공적으로 검색한다")
    void should_find_popular_clubs_successfully() {
        // Given
        when(clubSearchRepository.findPopularClubs(anyInt(), anyInt()))
                .thenReturn(createClubPage());

        // When
        ClubSearchResponse response = clubQueryService.findPopularClubs(0, 1);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.content()).hasSize(1);
        assertThat(response.content().getFirst().name()).isEqualTo(club.getName());
        verify(clubSearchRepository, times(1)).findPopularClubs(anyInt(), anyInt());
        verify(s3Service, times(1)).getDownloadUrl(anyLong());
    }
}

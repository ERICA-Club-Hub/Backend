//package kr.hanjari.backend.domain.club.application.command.impl;
//
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.CLUB_ID;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.NEW_CLUB_CODE;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.SCHEDULE_ID;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.clubDetailRequest;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createClub;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createClubBasicInformationRequest;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createClubDetailDraft;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createClubIntroductionRequest;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createClubRecruitmentRequest;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createClubRegistration;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createClubScheduleListRequest;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createNewClubScheduleListRequest;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createSchedule;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createScheduleDraft;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.anyLong;
//import static org.mockito.Mockito.anyString;
//import static org.mockito.Mockito.atLeastOnce;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//import kr.hanjari.backend.domain.club.application.command.CodeGenerator;
//import kr.hanjari.backend.domain.club.application.query.MailSender;
//import kr.hanjari.backend.domain.club.domain.entity.Club;
//import kr.hanjari.backend.domain.club.domain.entity.ClubRegistration;
//import kr.hanjari.backend.domain.club.domain.repository.ClubRegistrationRepository;
//import kr.hanjari.backend.domain.club.domain.repository.ClubRepository;
//import kr.hanjari.backend.domain.club.domain.repository.detail.IntroductionRepository;
//import kr.hanjari.backend.domain.club.domain.repository.detail.RecruitmentRepository;
//import kr.hanjari.backend.domain.club.domain.repository.detail.ScheduleRepository;
//import kr.hanjari.backend.domain.club.domain.repository.draft.ClubDetailDraftRepository;
//import kr.hanjari.backend.domain.club.domain.repository.draft.IntroductionDraftRepository;
//import kr.hanjari.backend.domain.club.domain.repository.draft.RecruitmentDraftRepository;
//import kr.hanjari.backend.domain.club.domain.repository.draft.ScheduleDraftRepository;
//import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationRequest;
//import kr.hanjari.backend.domain.club.presentation.dto.response.ClubCommandResponse;
//import kr.hanjari.backend.domain.file.application.FileService;
//import kr.hanjari.backend.domain.file.domain.entity.File;
//import kr.hanjari.backend.domain.file.domain.repository.FileRepository;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.mock.web.MockMultipartFile;
//
//@ExtendWith(MockitoExtension.class)
//class ClubCommandServiceImplTest {
//
//    @InjectMocks
//    private ClubCommandServiceImpl clubCommandService;
//
//    @Mock
//    private FileRepository fileRepository;
//    @Mock
//    private ClubRepository clubRepository;
//    @Mock
//    private ClubRegistrationRepository clubRegistrationRepository;
//    @Mock
//    private IntroductionRepository introductionRepository;
//    @Mock
//    private RecruitmentRepository recruitmentRepository;
//    @Mock
//    private ScheduleRepository scheduleRepository;
//    @Mock
//    private IntroductionDraftRepository introductionDraftRepository;
//    @Mock
//    private RecruitmentDraftRepository recruitmentDraftRepository;
//    @Mock
//    private ClubDetailDraftRepository clubDetailDraftRepository;
//    @Mock
//    private ScheduleDraftRepository scheduleDraftRepository;
//    @Mock
//    private CodeGenerator codeGenerator;
//    @Mock
//    private MailSender mailSender;
//    @Mock
//    private FileService fileService;
//
//    @Test
//    @DisplayName("동아리를 성공적으로 등록 요청한다.")
//    void should_request_club_registration() {
//        // given
//        ClubBasicInformationRequest request = createClubBasicInformationRequest();
//        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());
//        when(fileService.uploadObjectAndSaveFile(any(MockMultipartFile.class))).thenReturn(1L);
//        when(fileRepository.getReferenceById(1L)).thenReturn(new File());
//
//        // when
//        ClubCommandResponse response = clubCommandService.requestClubRegistration(request, file);
//
//        // then
//        assertNotNull(response);
//        verify(clubRegistrationRepository, times(1)).save(any(ClubRegistration.class));
//    }
//
//    @Test
//    @DisplayName("동아리 등록 신청을 승인한다.")
//    void should_accept_club_registration() {
//        // given
//        Long clubRegistrationId = 1L;
//        ClubRegistration clubRegistration = createClubRegistration();
//        when(clubRegistrationRepository.findById(clubRegistrationId)).thenReturn(Optional.of(clubRegistration));
//        when(codeGenerator.reissueCode(anyLong())).thenReturn(NEW_CLUB_CODE);
//        when(clubRepository.save(any(Club.class))).thenReturn(createClub());
//
//        // when
//        ClubCommandResponse response = clubCommandService.acceptClubRegistration(clubRegistrationId);
//
//        // then
//        assertNotNull(response);
//        verify(clubRepository, times(1)).save(any(Club.class));
//        verify(clubRegistrationRepository, times(1)).deleteById(clubRegistrationId);
//        verify(codeGenerator, times(1)).reissueCode(anyLong());
//        verify(mailSender, times(1)).sendEmail(anyString(), anyString(), anyString(), any());
//    }
//
//    @Test
//    @DisplayName("동아리 등록 신청을 삭제한다.")
//    void should_delete_club_registration() {
//        // given
//        Long clubRegistrationId = 1L;
//        ClubRegistration clubRegistration = createClubRegistration();
//        File file = new File();
//        clubRegistration.updateImageFile(file);
//        when(clubRegistrationRepository.findById(clubRegistrationId)).thenReturn(Optional.of(clubRegistration));
//
//        // when
//        clubCommandService.deleteClubRegistration(clubRegistrationId);
//
//        // then
//        verify(fileService, times(1)).deleteObjectAndFile(any());
//        verify(clubRegistrationRepository, times(1)).deleteById(clubRegistrationId);
//    }
//
//    @Test
//    @DisplayName("동아리 상세 정보를 저장한다.")
//    void should_save_club_detail() {
//        // given
//        Club club = createClub();
//        when(clubRepository.findById(CLUB_ID)).thenReturn(Optional.of(club));
//        when(clubRepository.save(any(Club.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // when
//        clubCommandService.saveClubDetail(CLUB_ID, clubDetailRequest());
//
//        // then
//        verify(clubRepository, times(1)).save(any(Club.class));
//    }
//
////    @Test
////    @Disabled
////    @DisplayName("동아리 기본 정보를 수정한다. (파일 없음)")
////    void should_update_club_basic_information_without_file() {
////        // given
////        Club club = createClub();
////        File file = new File();
////        club.updateClubImage(file);
////        ClubBasicInformationRequest request = createClubBasicInformationRequest();
////        when(clubRepository.findById(CLUB_ID)).thenReturn(Optional.of(club));
////        when(clubRepository.save(any(Club.class))).thenAnswer(invocation -> invocation.getArgument(0));
////
////        // when
////        clubCommandService.updateClubBasicInformation(CLUB_ID, request, null);
////
////        // then
////        verify(clubRepository, times(1)).save(any(Club.class));
////        assertEquals(request.clubName(), club.getName());
////        assertEquals(request.leaderEmail(), club.getLeaderEmail());
////        assertEquals(request.oneLiner(), club.getOneLiner());
////        assertEquals(request.briefIntroduction(), club.getBriefIntroduction());
////        verify(fileService, times(0)).uploadObjectAndSaveFile(any());
////        verify(fileService, times(0)).deleteObjectAndFile(any());
////    }
//
////    @Test
////    @Disabled
////    @DisplayName("동아리 기본 정보를 수정한다. (파일 있음)")
////    void should_update_club_basic_information_with_file() {
////        // given
////        Club club = createClub();
////        File oldFile = File.builder().id(99L).build();
////        club.updateClubImage(oldFile);
////        ClubBasicInformationRequest request = createClubBasicInformationRequest();
////        MockMultipartFile newFile = new MockMultipartFile("file", "new_test.jpg", "image/jpeg", "new_test".getBytes());
////
////        when(clubRepository.findById(CLUB_ID)).thenReturn(Optional.of(club));
////        when(fileService.uploadObjectAndSaveFile(any(MockMultipartFile.class))).thenReturn(100L);
////        when(fileRepository.getReferenceById(100L)).thenReturn(File.builder().id(100L).build());
////        when(clubRepository.save(any(Club.class))).thenAnswer(invocation -> invocation.getArgument(0));
////
////        // when
////        clubCommandService.updateClubBasicInformation(CLUB_ID, request, newFile);
////
////        // then
////        verify(clubRepository, times(1)).save(any(Club.class));
////        verify(fileService, times(1)).uploadObjectAndSaveFile(newFile);
////        verify(fileService, times(1)).deleteObjectAndFile(oldFile.getId());
////        assertEquals(request.clubName(), club.getName());
////        assertEquals(request.leaderEmail(), club.getLeaderEmail());
////        assertEquals(request.oneLiner(), club.getOneLiner());
////        assertEquals(request.briefIntroduction(), club.getBriefIntroduction());
////        assertNotNull(club.getImageFile());
////        assertEquals(100L, club.getImageFile().getId());
////    }
//
//    @Test
//    @DisplayName("동아리 상세 정보를 임시 저장 한다.")
//    void should_save_club_detail_draft() {
//        // given
//        when(clubRepository.existsById(CLUB_ID)).thenReturn(true);
//        when(clubDetailDraftRepository.findById(CLUB_ID)).thenReturn(Optional.empty());
//        when(clubDetailDraftRepository.save(any())).thenReturn(createClubDetailDraft(CLUB_ID));
//
//        // when
//        clubCommandService.saveClubDetailDraft(CLUB_ID, clubDetailRequest());
//
//        // then
//        verify(clubDetailDraftRepository, times(1)).save(any());
//    }
//
//    @Test
//    @DisplayName("동아리 월 별 일정을 생성하고 저장한다.")
//    void should_create_club_schedule() {
//        // given
//        Club club = createClub();
//        when(clubRepository.findById(CLUB_ID)).thenReturn(Optional.of(club));
//        when(scheduleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // when
//        clubCommandService.saveAndUpdateClubSchedule(CLUB_ID, createNewClubScheduleListRequest());
//
//        // then
//        verify(scheduleRepository, atLeastOnce()).save(any());
//        verify(scheduleDraftRepository, atLeastOnce()).deleteByClubId(CLUB_ID);
//    }
//
//    @Test
//    @DisplayName("동아리 월 별 일정을 업데이트한다.")
//    void should_save_and_update_club_schedule() {
//        // given
//        Club club = createClub();
//        when(clubRepository.findById(CLUB_ID)).thenReturn(Optional.of(club));
//        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(createSchedule(club)));
//        when(scheduleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // when
//        clubCommandService.saveAndUpdateClubSchedule(CLUB_ID, createClubScheduleListRequest());
//
//        // then
//        verify(scheduleRepository, atLeastOnce()).save(any());
//    }
//
//    @Test
//    @DisplayName("동아리 월 별 일정 임시본을 저장한다.")
//    void should_create_club_schedule_draft() {
//        // given
//        Club club = createClub();
//        when(clubRepository.findById(CLUB_ID)).thenReturn(Optional.of(club));
//        when(scheduleDraftRepository.save(any())).thenReturn(createScheduleDraft(club));
//
//        // when
//        clubCommandService.saveAndUpdateClubScheduleDraft(CLUB_ID, createNewClubScheduleListRequest());
//
//        // then
//        verify(scheduleDraftRepository, atLeastOnce()).save(any());
//    }
//
//    @Test
//    @DisplayName("동아리 월 별 일정 임시본을 수정하고 임시저장한다.")
//    void should_save_and_update_club_schedule_draft() {
//        // given
//        Club club = createClub();
//        when(clubRepository.findById(CLUB_ID)).thenReturn(Optional.of(club));
//        when(scheduleDraftRepository.findById(anyLong())).thenReturn(Optional.of(createScheduleDraft(club)));
//        when(scheduleDraftRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // when
//        clubCommandService.saveAndUpdateClubScheduleDraft(CLUB_ID, createClubScheduleListRequest());
//
//        // then
//        verify(scheduleDraftRepository, atLeastOnce()).save(any());
//    }
//
//    @Test
//    @DisplayName("동아리 월 별 일정을 삭제한다.")
//    void should_delete_club_schedule() {
//        // given
//        Club club = createClub();
//        when(clubRepository.findById(CLUB_ID)).thenReturn(Optional.of(club));
//        when(scheduleRepository.findById(SCHEDULE_ID)).thenReturn(Optional.of(createSchedule(club)));
//
//        // when
//        clubCommandService.deleteClubSchedule(CLUB_ID, SCHEDULE_ID);
//
//        // then
//        verify(scheduleRepository, times(1)).deleteById(SCHEDULE_ID);
//    }
//
//    @Test
//    @DisplayName("동아리 소개를 저장한다.")
//    void should_save_club_introduction() {
//        // given
//        when(clubRepository.existsById(CLUB_ID)).thenReturn(true);
//        when(introductionRepository.findById(CLUB_ID)).thenReturn(Optional.empty());
//        when(introductionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // when
//        clubCommandService.saveClubIntroduction(CLUB_ID, createClubIntroductionRequest());
//
//        // then
//        verify(introductionRepository, times(1)).save(any());
//    }
//
//    @Test
//    @DisplayName("동아리 소개를 임시저장한다.")
//    void should_save_club_introduction_draft() {
//        // given
//        when(clubRepository.existsById(CLUB_ID)).thenReturn(true);
//        when(introductionDraftRepository.findById(CLUB_ID)).thenReturn(Optional.empty());
//        when(introductionDraftRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // when
//        clubCommandService.saveClubIntroductionDraft(CLUB_ID, createClubIntroductionRequest());
//
//        // then
//        verify(introductionDraftRepository, times(1)).save(any());
//    }
//
//    @Test
//    @DisplayName("동아리 모집 안내를 저장한다.")
//    void should_save_club_recruitment() {
//        // given
//        when(clubRepository.existsById(CLUB_ID)).thenReturn(true);
//        when(recruitmentRepository.findById(CLUB_ID)).thenReturn(Optional.empty());
//        when(recruitmentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // when
//        clubCommandService.saveClubRecruitment(CLUB_ID, createClubRecruitmentRequest());
//
//        // then
//        verify(recruitmentRepository, times(1)).save(any());
//    }
//
//    @Test
//    @DisplayName("동아리 모집 안내를 임시저장한다.")
//    void should_save_club_recruitment_draft() {
//        // given
//        when(clubRepository.existsById(CLUB_ID)).thenReturn(true);
//        when(recruitmentDraftRepository.findById(CLUB_ID)).thenReturn(Optional.empty());
//        when(recruitmentDraftRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // when
//        clubCommandService.saveClubRecruitmentDraft(CLUB_ID, createClubRecruitmentRequest());
//
//        // then
//        verify(recruitmentDraftRepository, times(1)).save(any());
//    }
//
//    @Test
//    @DisplayName("동아리 조회수를 증가시킨다.")
//    void should_increase_club_view_count() {
//        // given
//        Club club = createClub();
//        when(clubRepository.findById(CLUB_ID)).thenReturn(Optional.of(club));
//
//        // when
//        clubCommandService.incrementClubViewCount(CLUB_ID);
//
//        // then
//        verify(clubRepository, times(1)).save(any(Club.class));
//        assertEquals(1L, club.getViewCount());
//    }
//}

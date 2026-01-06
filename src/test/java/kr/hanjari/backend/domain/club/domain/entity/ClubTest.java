//package kr.hanjari.backend.domain.club.domain.entity;
//
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.NEW_CLUB_CODE;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.clubDetailRequest;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createClub;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createClubBasicInformationRequest;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createClubRegistration;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createSchedule;
//import static kr.hanjari.backend.domain.club.fixtures.ClubTestFixture.createScheduleDraft;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;
//import kr.hanjari.backend.domain.club.domain.entity.draft.ScheduleDraft;
//import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
//import kr.hanjari.backend.domain.club.domain.enums.ClubType;
//import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;
//import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationRequest;
//import kr.hanjari.backend.domain.club.presentation.dto.request.ClubDetailRequest;
//import kr.hanjari.backend.domain.common.command.CategoryCommand;
//import kr.hanjari.backend.domain.file.domain.entity.File;
//import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
//import kr.hanjari.backend.global.payload.exception.GeneralException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//class ClubTest {
//
//    private Club club;
//    private File mockFile;
//
//    @BeforeEach
//    void setUp() {
//        club = createClub();
//        mockFile = mock(File.class);
//        when(mockFile.getId()).thenReturn(1L);
//    }
//
//    @Test
//    @DisplayName("ClubRegistration으로부터 Club을 성공적으로 생성한다")
//    void should_create_club_from_registration_successfully() {
//        // Given
//        ClubRegistration registration = createClubRegistration();
//        registration.updateImageFile(mockFile);
//
//        // When
//        Club newClub = Club.create(registration);
//
//        // Then
//        assertThat(newClub).isNotNull();
//        assertThat(newClub.getName()).isEqualTo(registration.getName());
//        assertThat(newClub.getLeaderEmail()).isEqualTo(registration.getLeaderEmail());
//        assertThat(newClub.getOneLiner()).isEqualTo(registration.getOneLiner());
//        assertThat(newClub.getBriefIntroduction()).isEqualTo(registration.getBriefIntroduction());
//        assertThat(newClub.getCategoryInfo()).isEqualTo(registration.getCategoryInfo());
//        assertThat(newClub.getImageFile()).isEqualTo(registration.getImageFile());
//        assertThat(newClub.getRecruitmentStatus()).isEqualTo(RecruitmentStatus.UPCOMING);
//    }
//
//    @Test
//    @DisplayName("동아리 이미지를 성공적으로 업데이트한다")
//    void should_update_club_image_successfully() {
//        // Given
//        File newMockFile = mock(File.class);
//        when(newMockFile.getId()).thenReturn(2L);
//
//        // When
//        club.updateClubImage(newMockFile);
//
//        // Then
//        assertThat(club.getImageFile()).isEqualTo(newMockFile);
//    }
//
//    @Test
//    @DisplayName("동아리 세부 정보를 성공적으로 업데이트한다")
//    void should_update_club_details_successfully() {
//        // Given
//        ClubDetailRequest detailRequest = clubDetailRequest();
//
//        // When
//        club.updateClubDetails(detailRequest);
//
//        // Then
//        assertThat(club.getRecruitmentStatus()).isEqualTo(detailRequest.recruitmentStatus());
//        assertThat(club.getLeaderName()).isEqualTo(detailRequest.leaderName());
//        assertThat(club.getLeaderPhone()).isEqualTo(detailRequest.leaderPhone());
//        assertThat(club.getMembershipFee()).isEqualTo(detailRequest.membershipFee());
//        assertThat(club.getMeetingSchedule()).isEqualTo(detailRequest.activities());
//        assertThat(club.getSnsUrl()).isEqualTo(detailRequest.snsUrl());
//        assertThat(club.getApplicationUrl()).isEqualTo(detailRequest.applicationUrl());
//    }
//
//    @Test
//    @DisplayName("동아리 공통 정보를 성공적으로 업데이트한다")
//    void should_update_club_common_info_successfully() {
//        // Given
//        ClubBasicInformationRequest commonInfoRequest = createClubBasicInformationRequest();
//        CategoryCommand categoryCommand = new CategoryCommand(ClubType.CENTRAL, CentralClubCategory.ACADEMIC, null,
//                null, null);
//
//        // When
//        club.updateClubCommonInfo(commonInfoRequest, categoryCommand);
//
//        // Then
//        assertThat(club.getName()).isEqualTo(commonInfoRequest.clubName());
//        assertThat(club.getLeaderEmail()).isEqualTo(commonInfoRequest.leaderEmail());
//        assertThat(club.getOneLiner()).isEqualTo(commonInfoRequest.oneLiner());
//        assertThat(club.getBriefIntroduction()).isEqualTo(commonInfoRequest.briefIntroduction());
//        assertThat(club.getCategoryInfo().getClubType()).isEqualTo(categoryCommand.type());
//        assertThat(club.getCategoryInfo().getCentralCategory()).isEqualTo(categoryCommand.central());
//    }
//
//    @Test
//    @DisplayName("동아리 코드를 성공적으로 업데이트한다")
//    void should_update_club_code_successfully() {
//        // Given
//        String newCode = NEW_CLUB_CODE;
//
//        // When
//        club.updateCode(newCode);
//
//        // Then
//        assertThat(club.getCode()).isEqualTo(newCode);
//    }
//
//    @Test
//    @DisplayName("모집 상태를 UPCOMING으로 성공적으로 업데이트한다")
//    void should_update_recruitment_status_to_upcoming_successfully() {
//        // When
//        club.updateRecruitmentStatus(0);
//
//        // Then
//        assertThat(club.getRecruitmentStatus()).isEqualTo(RecruitmentStatus.UPCOMING);
//    }
//
//    @Test
//    @DisplayName("모집 상태를 OPEN으로 성공적으로 업데이트한다")
//    void should_update_recruitment_status_to_open_successfully() {
//        // When
//        club.updateRecruitmentStatus(1);
//
//        // Then
//        assertThat(club.getRecruitmentStatus()).isEqualTo(RecruitmentStatus.OPEN);
//    }
//
//    @Test
//    @DisplayName("모집 상태를 CLOSED로 성공적으로 업데이트한다")
//    void should_update_recruitment_status_to_closed_successfully() {
//        // When
//        club.updateRecruitmentStatus(2);
//
//        // Then
//        assertThat(club.getRecruitmentStatus()).isEqualTo(RecruitmentStatus.CLOSED);
//    }
//
//    @Test
//    @DisplayName("유효하지 않은 옵션으로 모집 상태 업데이트 시 기존 상태를 유지한다")
//    void should_maintain_recruitment_status_with_invalid_option() {
//        // Given
//        RecruitmentStatus originalStatus = club.getRecruitmentStatus();
//
//        // When
//        club.updateRecruitmentStatus(99);
//
//        // Then
//        assertThat(club.getRecruitmentStatus()).isEqualTo(originalStatus);
//    }
//
//    @Test
//    @DisplayName("조회수를 성공적으로 증가시킨다")
//    void should_increment_view_count_successfully() {
//        // Given
//        Long initialViewCount = club.getViewCount();
//
//        // When
//        club.incrementViewCount();
//
//        // Then
//        assertThat(club.getViewCount()).isEqualTo(initialViewCount + 1);
//    }
//
//    @Test
//    @DisplayName("동아리에 속한 일정인 경우 유효성 검사를 성공한다")
//    void should_validate_schedule_contains_in_club_successfully() {
//        // Given
//        Schedule schedule = createSchedule(club);
//
//        // When & Then
//        assertDoesNotThrow(() -> club.validateIsScheduleContainsInClub(schedule));
//    }
//
//    @Test
//    @DisplayName("동아리에 속하지 않은 일정인 경우 GeneralException을 발생시킨다")
//    void should_throw_exception_when_schedule_not_belong_to_club() {
//        // Given
//        Club anotherClub = Club.builder().id(99L).build();
//        Schedule schedule = createSchedule(anotherClub);
//
//        // When & Then
//        GeneralException exception = assertThrows(GeneralException.class,
//                () -> club.validateIsScheduleContainsInClub(schedule));
//        assertThat(exception.getCode()).isEqualTo(ErrorStatus._SCHEDULE_IS_NOT_BELONG_TO_CLUB);
//    }
//
//    @Test
//    @DisplayName("동아리에 속한 일정 초안인 경우 유효성 검사를 성공한다")
//    void should_validate_schedule_draft_contains_in_club_successfully() {
//        // Given
//        ScheduleDraft scheduleDraft = createScheduleDraft(club);
//
//        // When & Then
//        assertDoesNotThrow(() -> club.validateIsScheduleDraftContainsInClub(scheduleDraft));
//    }
//
//    @Test
//    @DisplayName("동아리에 속하지 않은 일정 초안인 경우 GeneralException을 발생시킨다")
//    void should_throw_exception_when_schedule_draft_not_belong_to_club() {
//        // Given
//        Club anotherClub = Club.builder().id(99L).build();
//        ScheduleDraft scheduleDraft = createScheduleDraft(anotherClub);
//
//        // When & Then
//        GeneralException exception = assertThrows(GeneralException.class,
//                () -> club.validateIsScheduleDraftContainsInClub(scheduleDraft));
//        assertThat(exception.getCode()).isEqualTo(ErrorStatus._SCHEDULE_IS_NOT_BELONG_TO_CLUB);
//    }
//}

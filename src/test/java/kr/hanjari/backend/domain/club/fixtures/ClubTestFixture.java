package kr.hanjari.backend.domain.club.fixtures;

import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.ClubCategoryInfo;
import kr.hanjari.backend.domain.club.domain.entity.ClubRegistration;
import kr.hanjari.backend.domain.club.domain.entity.detail.Introduction;
import kr.hanjari.backend.domain.club.domain.entity.detail.Recruitment;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;
import kr.hanjari.backend.domain.club.domain.entity.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.IntroductionDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.RecruitmentDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.ScheduleDraft;
import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.domain.enums.ClubType;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubDetailRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubIntroductionRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubRecruitmentRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubScheduleListRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubScheduleRequest;
import kr.hanjari.backend.domain.common.command.CategoryCommand;

public class ClubTestFixture {

    // Club Constants
    public static final Long CLUB_ID = 1L;
    public static final String CLUB_CODE = "TEST_CODE";
    public static final String NEW_CLUB_CODE = "NEW_TEST_CODE";
    public static final String CLUB_NAME = "Test Club";
    public static final String CLUB_LEADER_NAME = "Test Leader";
    public static final String CLUB_LEADER_EMAIL = "test@leader.com";
    public static final String CLUB_LEADER_PHONE = "010-1234-5678";
    public static final String CLUB_ONE_LINER = "A club for testing.";
    public static final String CLUB_BRIEF_INTRODUCTION = "This is a brief introduction for the test club.";
    public static final String CLUB_MEETING_SCHEDULE = "Every Friday";
    public static final Integer CLUB_MEMBERSHIP_FEE = 10000;
    public static final String CLUB_SNS_URL = "https://test.club.com";
    public static final String CLUB_APPLICATION_URL = "https://apply.test.club.com";
    public static final Long CLUB_VIEW_COUNT = 0L;
    public static final RecruitmentStatus CLUB_RECRUITMENT_STATUS = RecruitmentStatus.OPEN;

    // ClubRegistration Constants
    public static final Long CLUB_REGISTRATION_ID = 1L;
    public static final String CLUB_REGISTRATION_NAME = "Test Club Registration";
    public static final String CLUB_REGISTRATION_LEADER_EMAIL = "test@leader.com";
    public static final String CLUB_REGISTRATION_ONE_LINER = "A club for testing registration.";
    public static final String CLUB_REGISTRATION_BRIEF_INTRODUCTION = "This is a brief introduction for the test club registration.";

    // Introduction Constants
    public static final String INTRODUCTION_CONTENT_1 = "Introduction content 1";
    public static final String INTRODUCTION_CONTENT_2 = "Introduction content 2";
    public static final String INTRODUCTION_CONTENT_3 = "Introduction content 3";

    // Recruitment Constants
    public static final String RECRUITMENT_CONTENT_1 = "Recruitment content 1";
    public static final String RECRUITMENT_CONTENT_2 = "Recruitment content 2";
    public static final String RECRUITMENT_CONTENT_3 = "Recruitment content 3";

    // Schedule Constants
    public static final Long SCHEDULE_ID = 1L;
    public static final Integer SCHEDULE_MONTH = 10;
    public static final String SCHEDULE_CONTENT = "Monthly schedule content";

    // ClubDetailDraft Constants
    public static final String DRAFT_ONE_LINER = "Draft one-liner";
    public static final RecruitmentStatus DRAFT_RECRUITMENT_STATUS = RecruitmentStatus.UPCOMING;
    public static final String DRAFT_LEADER_NAME = "Draft Leader";
    public static final String DRAFT_LEADER_PHONE = "010-8765-4321";
    public static final String DRAFT_ACTIVITIES = "Draft activities";
    public static final Integer DRAFT_MEMBERSHIP_FEE = 20000;
    public static final String DRAFT_SNS_URL = "https://draft.club.com";
    public static final String DRAFT_APPLICATION_URL = "https://apply.draft.club.com";

    // IntroductionDraft Constants
    public static final String DRAFT_INTRODUCTION_CONTENT_1 = "Draft introduction content 1";
    public static final String DRAFT_INTRODUCTION_CONTENT_2 = "Draft introduction content 2";
    public static final String DRAFT_INTRODUCTION_CONTENT_3 = "Draft introduction content 3";

    // RecruitmentDraft Constants
    public static final String DRAFT_RECRUITMENT_CONTENT_1 = "Draft recruitment content 1";
    public static final String DRAFT_RECRUITMENT_CONTENT_2 = "Draft recruitment content 2";
    public static final String DRAFT_RECRUITMENT_CONTENT_3 = "Draft recruitment content 3";

    // ScheduleDraft Constants
    public static final Long DRAFT_SCHEDULE_ID = 1L;
    public static final Integer DRAFT_SCHEDULE_MONTH = 11;
    public static final String DRAFT_SCHEDULE_CONTENT = "Draft monthly schedule content";
    public static final String NEW_CLUB_NAME = "New Club Name";
    public static final String NEW_CLUB_EMAIL = "new.leader@email.com";
    public static final String NEW_ONE_LINER = "New one-liner";
    public static final String NEW_BRIEF_INTRODUCTION = "New brief introduction";

    public static Club createClub() {
        return Club.builder()
                .id(CLUB_ID)
                .code(CLUB_CODE)
                .name(CLUB_NAME)
                .leaderName(CLUB_LEADER_NAME)
                .leaderEmail(CLUB_LEADER_EMAIL)
                .leaderPhone(CLUB_LEADER_PHONE)
                .oneLiner(CLUB_ONE_LINER)
                .briefIntroduction(CLUB_BRIEF_INTRODUCTION)
                .meetingSchedule(CLUB_MEETING_SCHEDULE)
                .membershipFee(CLUB_MEMBERSHIP_FEE)
                .snsUrl(CLUB_SNS_URL)
                .applicationUrl(CLUB_APPLICATION_URL)
                .viewCount(CLUB_VIEW_COUNT)
                .recruitmentStatus(CLUB_RECRUITMENT_STATUS)
                .categoryInfo(createClubCategoryInfo())
                .build();
    }

    public static ClubCategoryInfo createClubCategoryInfo() {
        return ClubCategoryInfo.from(
                new CategoryCommand(ClubType.CENTRAL, CentralClubCategory.ACADEMIC, null, null, null));
    }

    public static ClubRegistration createClubRegistration() {
        return ClubRegistration.builder()
                .id(CLUB_REGISTRATION_ID)
                .name(CLUB_REGISTRATION_NAME)
                .leaderEmail(CLUB_REGISTRATION_LEADER_EMAIL)
                .oneLiner(CLUB_REGISTRATION_ONE_LINER)
                .briefIntroduction(CLUB_REGISTRATION_BRIEF_INTRODUCTION)
                .categoryInfo(createClubCategoryInfo())
                .build();
    }

    public static Introduction createIntroduction(Club club) {
        return Introduction.builder()
                .clubId(club.getId())
                .club(club)
                .content1(INTRODUCTION_CONTENT_1)
                .content2(INTRODUCTION_CONTENT_2)
                .content3(INTRODUCTION_CONTENT_3)
                .build();
    }

    public static Recruitment createRecruitment(Club club) {
        return Recruitment.builder()
                .clubId(club.getId())
                .club(club)
                .content1(RECRUITMENT_CONTENT_1)
                .content2(RECRUITMENT_CONTENT_2)
                .content3(RECRUITMENT_CONTENT_3)
                .build();
    }

    public static Schedule createSchedule(Club club) {
        return Schedule.builder()
                .id(SCHEDULE_ID)
                .club(club)
                .month(SCHEDULE_MONTH)
                .content(SCHEDULE_CONTENT)
                .build();
    }

    public static ClubDetailDraft createClubDetailDraft(Long clubId) {
        return ClubDetailDraft.builder()
                .clubId(clubId)
                .oneLiner(DRAFT_ONE_LINER)
                .recruitmentStatus(DRAFT_RECRUITMENT_STATUS)
                .leaderName(DRAFT_LEADER_NAME)
                .leaderPhone(DRAFT_LEADER_PHONE)
                .activities(DRAFT_ACTIVITIES)
                .membershipFee(DRAFT_MEMBERSHIP_FEE)
                .snsUrl(DRAFT_SNS_URL)
                .applicationUrl(DRAFT_APPLICATION_URL)
                .build();
    }

    public static IntroductionDraft createIntroductionDraft(Club club) {
        return IntroductionDraft.builder()
                .clubId(club.getId())
                .club(club)
                .content1(DRAFT_INTRODUCTION_CONTENT_1)
                .content2(DRAFT_INTRODUCTION_CONTENT_2)
                .content3(DRAFT_INTRODUCTION_CONTENT_3)
                .build();
    }

    public static RecruitmentDraft createRecruitmentDraft(Club club) {
        return RecruitmentDraft.builder()
                .clubId(club.getId())
                .club(club)
                .content1(DRAFT_RECRUITMENT_CONTENT_1)
                .content2(DRAFT_RECRUITMENT_CONTENT_2)
                .content3(DRAFT_RECRUITMENT_CONTENT_3)
                .build();
    }

    public static ScheduleDraft createScheduleDraft(Club club) {
        return ScheduleDraft.builder()
                .id(DRAFT_SCHEDULE_ID)
                .club(club)
                .month(DRAFT_SCHEDULE_MONTH)
                .content(DRAFT_SCHEDULE_CONTENT)
                .build();

    }


    public static ClubBasicInformationRequest createClubBasicInformationRequest() {
        return new ClubBasicInformationRequest(
                NEW_CLUB_NAME,
                NEW_CLUB_EMAIL,
                ClubType.CENTRAL,
                new ClubBasicInformationRequest.Category(
                        CentralClubCategory.ACADEMIC,
                        null,
                        null,
                        null
                ),
                NEW_ONE_LINER,
                NEW_BRIEF_INTRODUCTION
        );
    }

    public static ClubDetailRequest clubDetailRequest() {
        return new ClubDetailRequest(
                RecruitmentStatus.OPEN,
                CLUB_LEADER_NAME,
                CLUB_LEADER_PHONE,
                CLUB_MEETING_SCHEDULE,
                CLUB_MEMBERSHIP_FEE,
                CLUB_SNS_URL,
                CLUB_APPLICATION_URL
        );
    }

    public static ClubScheduleListRequest createClubScheduleListRequest() {
        return new ClubScheduleListRequest(
                List.of(new ClubScheduleRequest(
                        SCHEDULE_MONTH,
                        SCHEDULE_CONTENT,
                        SCHEDULE_ID)
                )
        );
    }

    public static ClubScheduleListRequest createNewClubScheduleListRequest() {
        return new ClubScheduleListRequest(
                List.of(new ClubScheduleRequest(
                        SCHEDULE_MONTH,
                        SCHEDULE_CONTENT,
                        null)
                )
        );
    }

    public static ClubIntroductionRequest createClubIntroductionRequest() {
        return new ClubIntroductionRequest(
                INTRODUCTION_CONTENT_1,
                INTRODUCTION_CONTENT_2,
                INTRODUCTION_CONTENT_3
        );
    }

    public static ClubRecruitmentRequest createClubRecruitmentRequest() {
        return new ClubRecruitmentRequest(
                RECRUITMENT_CONTENT_1,
                RECRUITMENT_CONTENT_2,
                RECRUITMENT_CONTENT_3
        );
    }
}

    
package kr.hanjari.backend.domain.club.application.query;


import kr.hanjari.backend.domain.club.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.enums.College;
import kr.hanjari.backend.domain.club.enums.Department;
import kr.hanjari.backend.domain.club.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.enums.SortBy;
import kr.hanjari.backend.domain.club.enums.UnionClubCategory;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubDetailListResponse;
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

public interface ClubQueryService {

    // 등록 요청 동아리 조회
    GetRegistrationsResponse getRegistrations();

    // 조건 별 동아리 검색
    ClubDetailListResponse findClubsByCondition(
            String name, CentralClubCategory category, RecruitmentStatus status, SortBy sortBy, int page,
            int size);

    // 동아리 상세 조회
    ClubResponse findClubDetail(Long clubId);

    ClubOverviewResponse findClubOverview(Long clubId);

    ClubBasicInfoResponse findClubBasicInfo(Long clubId);

    ClubDetailDraftResponse findClubDetailDraft(Long clubId);

    // 동아리 월 별 일정 조회
    ClubScheduleResponse findAllClubActivities(Long clubId);

    ClubScheduleDraftResponse findAllClubActivitiesDraft(Long clubId);

    // 동아리 소개 조회
    ClubIntroductionResponse findClubIntroduction(Long clubId);

    ClubIntroductionDraftResponse findClubIntroductionDraft(Long clubId);

    // 동아리 모집 안내
    ClubRecruitmentResponse findClubRecruitment(Long clubId);

    ClubRecruitmentDraftResponse findClubRecruitmentDraft(Long clubId);

    // 동아리 카테고리 조회
    ClubSearchResponse findCentralClubsByCondition(
            String keyword, RecruitmentStatus status, SortBy sortBy, CentralClubCategory category, int page, int size);

    ClubSearchResponse findUnionClubsByCondition(
            String keyword, RecruitmentStatus status, SortBy sortBy, UnionClubCategory unionCategory, int page,
            int size);

    ClubSearchResponse findCollegeClubsByCondition(
            String keyword, RecruitmentStatus status, SortBy sortBy, College college, int page, int size);

    ClubSearchResponse findDepartmentClubsByCondition(
            String keyword, RecruitmentStatus status, SortBy sortBy, College college, Department department, int page,
            int size);

    // 현재 인기있는 동아리 조회
    ClubSearchResponse findPopularClubs(int page, int size);

}

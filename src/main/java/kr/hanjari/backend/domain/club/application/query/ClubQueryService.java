package kr.hanjari.backend.domain.club.application.query;


import kr.hanjari.backend.domain.club.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.enums.College;
import kr.hanjari.backend.domain.club.enums.Department;
import kr.hanjari.backend.domain.club.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.enums.SortBy;
import kr.hanjari.backend.domain.club.enums.UnionClubCategory;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubDetailListResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubIntroductionResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubOverviewResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubRecruitmentResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubScheduleResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubSearchResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.GetRegistrationsResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubBasicInfoResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubDetailDraftResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubIntroductionDraftResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubRecruitmentDraftResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubScheduleDraftResponseDTO;

public interface ClubQueryService {

    // 등록 요청 동아리 조회
    GetRegistrationsResponseDTO getRegistrations();

    // 조건 별 동아리 검색
    ClubDetailListResponseDTO findClubsByCondition(
            String name, CentralClubCategory category, RecruitmentStatus status, SortBy sortBy, int page,
            int size);

    // 동아리 상세 조회
    ClubResponseDTO findClubDetail(Long clubId);

    ClubOverviewResponseDTO findClubOverview(Long clubId);

    ClubBasicInfoResponseDTO findClubBasicInfo(Long clubId);

    ClubDetailDraftResponseDTO findClubDetailDraft(Long clubId);

    // 동아리 월 별 일정 조회
    ClubScheduleResponseDTO findAllClubActivities(Long clubId);

    ClubScheduleDraftResponseDTO findAllClubActivitiesDraft(Long clubId);

    // 동아리 소개 조회
    ClubIntroductionResponseDTO findClubIntroduction(Long clubId);

    ClubIntroductionDraftResponseDTO findClubIntroductionDraft(Long clubId);

    // 동아리 모집 안내
    ClubRecruitmentResponseDTO findClubRecruitment(Long clubId);

    ClubRecruitmentDraftResponseDTO findClubRecruitmentDraft(Long clubId);

    // 동아리 카테고리 조회
    ClubSearchResponseDTO findCentralClubsByCondition(
            String keyword, RecruitmentStatus status, SortBy sortBy, CentralClubCategory category, int page, int size);

    ClubSearchResponseDTO findUnionClubsByCondition(
            String keyword, RecruitmentStatus status, SortBy sortBy, UnionClubCategory unionCategory, int page,
            int size);

    ClubSearchResponseDTO findCollegeClubsByCondition(
            String keyword, RecruitmentStatus status, SortBy sortBy, College college, int page, int size);

    ClubSearchResponseDTO findDepartmentClubsByCondition(
            String keyword, RecruitmentStatus status, SortBy sortBy, College college, Department department, int page,
            int size);

    // 현재 인기있는 동아리 조회
    ClubSearchResponseDTO findPopularClubs(int page, int size);

}

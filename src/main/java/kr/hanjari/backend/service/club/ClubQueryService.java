package kr.hanjari.backend.service.club;


import kr.hanjari.backend.domain.enums.ClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.enums.SortBy;
import kr.hanjari.backend.web.dto.club.response.ClubDetailDraftResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubIntroductionDraftResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubIntroductionResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubRecruitmentDraftResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubRecruitmentResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubScheduleResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubSearchResponseDTO;

public interface ClubQueryService {

    // 조건 별 동아리 검색
    ClubSearchResponseDTO findClubsByCondition(
            String name, ClubCategory category, RecruitmentStatus status, SortBy sortBy, int page,
            int size);

    // 동아리 상세 조회
    ClubResponseDTO findClubDetail(Long clubId);
    ClubDetailDraftResponseDTO findClubDetailDraft(Long clubId);

    // 동아리 월 별 일정 조회
    ClubScheduleResponseDTO findAllClubActivities(Long clubId);

    // 동아리 소개 조회
    ClubIntroductionResponseDTO findClubIntroduction(Long clubId);
    ClubIntroductionDraftResponseDTO findClubIntroductionDraft(Long clubId);

    // 동아리 모집 안내
    ClubRecruitmentResponseDTO findClubRecruitment(Long clubId);
    ClubRecruitmentDraftResponseDTO findClubRecruitmentDraft(Long clubId);
}

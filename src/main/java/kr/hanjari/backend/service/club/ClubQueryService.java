package kr.hanjari.backend.service.club;

import static kr.hanjari.backend.web.dto.club.ClubResponseDTO.*;

import kr.hanjari.backend.domain.enums.ClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.enums.SortBy;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO.ClubSearchDTO;

public interface ClubQueryService {

    // 조건 별 동아리 검색
    ClubSearchDTO findClubsByCondition(
            String name, ClubCategory category, RecruitmentStatus status, SortBy sortBy, int page,
            int size);

    // 동아리 상세 조회
    ClubDTO findClubDetail(Long clubId);

    // 동아리 월 별 일정 조회
    ClubActivityDTO findAllClubActivities(Long clubId);

    // 동아리 소개 조회
    ClubIntroductionDTO findClubIntroduction(Long clubId);

    // 동아리 모집 안내
    ClubRecruitmentDTO findClubRecruitment(Long clubId);
}

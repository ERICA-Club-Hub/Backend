package kr.hanjari.backend.service.club;

import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubActivityDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubDetailDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubIntroductionDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubRecruitmentDTO;

public interface ClubCommandService {

    // 동아리 상세 정보
    Long saveClubDetail(ClubDetailDTO clubDetailDTO);
    Long updateClubDetail(Long clubId, ClubDetailDTO clubDetailDTO);
    void deleteClubDetail(Long clubId);

    // 동아리 월 별 일정
    Long saveClubActivity(Long clubId, ClubActivityDTO clubActivityDTO);
    Long updateClubActivity(Long clubId, Long activityId, ClubActivityDTO clubActivityDTO);
    void deleteClubActivity(Long clubId, Long activityId);

    // 동아리 소개
    Long saveClubIntroduction(Long clubId, ClubIntroductionDTO introduction);
    Long updateClubIntroduction(Long clubId, ClubIntroductionDTO introduction);
    void deleteClubIntroduction(Long clubId);

    // 동아리 모집 안내
    Long saveClubRecruitment(Long clubId, ClubRecruitmentDTO recruitment);
    Long updateClubRecruitment(Long clubId, ClubRecruitmentDTO recruitment);
    void deleteClubRecruitment(Long clubId);

}


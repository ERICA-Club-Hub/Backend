package kr.hanjari.backend.service.club;

import kr.hanjari.backend.domain.key.ScheduleId;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubDetailDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubIntroductionDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubRecruitmentDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubScheduleDTO;

public interface ClubCommandService {

    // 동아리 상세 정보
    Long saveClubDetail(Long clubId, ClubDetailDTO clubDetailDTO);

    // 동아리 월 별 일정
    ScheduleId saveClubSchedule(Long clubId, ClubScheduleDTO clubActivityDTO);
    ScheduleId updateClubSchedule(Long clubId, Integer month, ClubScheduleDTO clubActivityDTO);
    void deleteClubSchedule(Long clubId, Integer month);

    // 동아리 소개
    Long saveClubIntroduction(Long clubId, ClubIntroductionDTO introduction);

    // 동아리 모집 안내
    Long saveClubRecruitment(Long clubId, ClubRecruitmentDTO recruitment);

}


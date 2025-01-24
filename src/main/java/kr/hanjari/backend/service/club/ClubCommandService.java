package kr.hanjari.backend.service.club;

import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubDetailDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubIntroductionDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubRecruitmentDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubScheduleDTO;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO.ClubScheduleDTO.ScheduleDTO;

public interface ClubCommandService {

    // 동아리 상세 정보
    Long saveClubDetail(Long clubId, ClubDetailDTO clubDetailDTO);

    // 동아리 월 별 일정
    ScheduleDTO saveClubSchedule(Long clubId, ClubScheduleDTO clubActivityDTO);
    ScheduleDTO updateClubSchedule(Long clubId, Long scheduleId, ClubScheduleDTO clubActivityDTO);
    void deleteClubSchedule(Long clubId, Long scheduleId);

    // 동아리 소개
    Long saveClubIntroduction(Long clubId, ClubIntroductionDTO introduction);

    // 동아리 모집 안내
    Long saveClubRecruitment(Long clubId, ClubRecruitmentDTO recruitment);

}


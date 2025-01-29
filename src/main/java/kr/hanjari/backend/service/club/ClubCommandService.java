package kr.hanjari.backend.service.club;

import kr.hanjari.backend.web.dto.club.request.ClubDetailRequestDTO;
import kr.hanjari.backend.web.dto.club.request.ClubIntroductionRequestDTO;
import kr.hanjari.backend.web.dto.club.request.ClubRecruitmentRequestDTO;
import kr.hanjari.backend.web.dto.club.request.ClubScheduleRequestDTO;
import kr.hanjari.backend.web.dto.club.response.ScheduleResponseDTO;

public interface ClubCommandService {

    // 동아리 상세 정보
    Long saveClubDetail(Long clubId, ClubDetailRequestDTO clubDetailDTO);

    // 동아리 월 별 일정
    ScheduleResponseDTO saveClubSchedule(Long clubId, ClubScheduleRequestDTO clubActivityDTO);
    ScheduleResponseDTO updateClubSchedule(Long clubId, Long scheduleId, ClubScheduleRequestDTO clubActivityDTO);
    void deleteClubSchedule(Long clubId, Long scheduleId);

    // 동아리 소개
    Long saveClubIntroduction(Long clubId, ClubIntroductionRequestDTO introduction);

    // 동아리 모집 안내
    Long saveClubRecruitment(Long clubId, ClubRecruitmentRequestDTO recruitment);

}


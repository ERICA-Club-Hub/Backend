package kr.hanjari.backend.service.club;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.web.dto.club.request.*;
import kr.hanjari.backend.web.dto.club.response.ScheduleListResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ScheduleResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ClubCommandService {

    //동아리 등록 신청
    Long requestClubRegistration(CommonClubDTO reqeustBody, MultipartFile image);
    Long acceptClubRegistration(Long clubRegistrationId);

    // 동아리 상세 정보
    Long saveClubDetail(Long clubId, ClubDetailRequestDTO clubDetailDTO);
    Long updateClubDetail(Long clubId, CommonClubDTO request, MultipartFile file);
    Long saveClubDetailDraft(Long clubId, ClubDetailRequestDTO clubDetailDTO);

    // 동아리 월 별 일정
    ScheduleListResponseDTO saveClubSchedule(Long clubId, ClubScheduleListRequestDTO clubActivityDTO);
    ScheduleResponseDTO updateClubSchedule(Long clubId, Long scheduleId, ClubScheduleRequestDTO clubActivityDTO);
    void deleteClubSchedule(Long clubId, Long scheduleId);

    // 동아리 소개
    Long saveClubIntroduction(Long clubId, ClubIntroductionRequestDTO introduction);
    Long saveClubIntroductionDraft(Long clubId, ClubIntroductionRequestDTO introduction);

    // 동아리 모집 안내
    Long saveClubRecruitment(Long clubId, ClubRecruitmentRequestDTO recruitment);
    Long saveClubRecruitmentDraft(Long clubId, ClubRecruitmentRequestDTO recruitment);

}


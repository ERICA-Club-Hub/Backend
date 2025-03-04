package kr.hanjari.backend.service.club;

import kr.hanjari.backend.web.dto.club.request.*;
import kr.hanjari.backend.web.dto.club.response.ScheduleListResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ScheduleResponseDTO;
import kr.hanjari.backend.web.dto.club.response.draft.ClubScheduleDraftResponseDTO;
import kr.hanjari.backend.web.dto.club.response.draft.ScheduleDraftResponseDTO;
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
    ScheduleListResponseDTO saveAndUpdateClubSchedule(Long clubId, ClubScheduleListRequestDTO clubActivityDTO);
    ClubScheduleDraftResponseDTO saveAndUpdateClubScheduleDraft(Long clubId, ClubScheduleListRequestDTO clubActivityDTO);
    void deleteClubSchedule(Long clubId, Long scheduleId);

    // 동아리 소개
    Long saveClubIntroduction(Long clubId, ClubIntroductionRequestDTO introduction);
    Long saveClubIntroductionDraft(Long clubId, ClubIntroductionRequestDTO introduction);

    // 동아리 모집 안내
    Long saveClubRecruitment(Long clubId, ClubRecruitmentRequestDTO recruitment);
    Long saveClubRecruitmentDraft(Long clubId, ClubRecruitmentRequestDTO recruitment);

}


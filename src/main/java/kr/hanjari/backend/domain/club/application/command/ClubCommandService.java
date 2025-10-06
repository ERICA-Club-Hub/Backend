package kr.hanjari.backend.domain.club.application.command;

import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationDTO;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubDetailRequestDTO;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubIntroductionRequestDTO;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubRecruitmentRequestDTO;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubScheduleListRequestDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ScheduleListResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubScheduleDraftResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ClubCommandService {

    //동아리 등록 신청
    Long requestClubRegistration(ClubBasicInformationDTO reqeustBody, MultipartFile image);

    Long acceptClubRegistration(Long clubRegistrationId);

    void deleteClubRegistration(Long clubRegistrationId);

    // 동아리 기본 정보
    Long updateClubBasicInformation(Long clubId, ClubBasicInformationDTO request, MultipartFile file);

    // 동아리 상세 정보
    Long saveClubDetail(Long clubId, ClubDetailRequestDTO clubDetailDTO);

    Long saveClubDetailDraft(Long clubId, ClubDetailRequestDTO clubDetailDTO);

    // 동아리 월 별 일정
    ScheduleListResponseDTO saveAndUpdateClubSchedule(Long clubId, ClubScheduleListRequestDTO clubActivityDTO);

    ClubScheduleDraftResponseDTO saveAndUpdateClubScheduleDraft(Long clubId,
                                                                ClubScheduleListRequestDTO clubActivityDTO);

    void deleteClubSchedule(Long clubId, Long scheduleId);

    // 동아리 소개
    Long saveClubIntroduction(Long clubId, ClubIntroductionRequestDTO introduction);

    Long saveClubIntroductionDraft(Long clubId, ClubIntroductionRequestDTO introduction);

    // 동아리 모집 안내
    Long saveClubRecruitment(Long clubId, ClubRecruitmentRequestDTO recruitment);

    Long saveClubRecruitmentDraft(Long clubId, ClubRecruitmentRequestDTO recruitment);

    // 조회 수 증가
    void incrementClubViewCount(Long clubId);

}


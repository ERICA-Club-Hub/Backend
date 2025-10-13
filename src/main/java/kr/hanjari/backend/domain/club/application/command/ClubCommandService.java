package kr.hanjari.backend.domain.club.application.command;

import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubDetailRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubIntroductionRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubRecruitmentRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubScheduleListRequest;
import kr.hanjari.backend.domain.club.presentation.dto.response.ScheduleListResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubScheduleDraftResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ClubCommandService {

    //동아리 등록 신청
    Long requestClubRegistration(ClubBasicInformationRequest reqeustBody, MultipartFile image);

    Long acceptClubRegistration(Long clubRegistrationId);

    void deleteClubRegistration(Long clubRegistrationId);

    // 동아리 기본 정보
    Long updateClubBasicInformation(Long clubId, ClubBasicInformationRequest request, MultipartFile file);

    // 동아리 상세 정보
    Long saveClubDetail(Long clubId, ClubDetailRequest clubDetailDTO);

    Long saveClubDetailDraft(Long clubId, ClubDetailRequest clubDetailDTO);

    // 동아리 월 별 일정
    ScheduleListResponse saveAndUpdateClubSchedule(Long clubId, ClubScheduleListRequest clubActivityDTO);

    ClubScheduleDraftResponse saveAndUpdateClubScheduleDraft(Long clubId,
                                                             ClubScheduleListRequest clubActivityDTO);

    void deleteClubSchedule(Long clubId, Long scheduleId);

    // 동아리 소개
    Long saveClubIntroduction(Long clubId, ClubIntroductionRequest introduction);

    Long saveClubIntroductionDraft(Long clubId, ClubIntroductionRequest introduction);

    // 동아리 모집 안내
    Long saveClubRecruitment(Long clubId, ClubRecruitmentRequest recruitment);

    Long saveClubRecruitmentDraft(Long clubId, ClubRecruitmentRequest recruitment);

    // 조회 수 증가
    void incrementClubViewCount(Long clubId);

}


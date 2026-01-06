package kr.hanjari.backend.domain.club.application.command;

import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationUpdateRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubDetailRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubIntroductionRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubRecruitmentRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubScheduleListRequest;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubCommandResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ScheduleListResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubScheduleDraftResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ClubCommandService {

    //동아리 등록 신청
    ClubCommandResponse requestClubRegistration(ClubBasicInformationRequest requestBody, MultipartFile image);

    ClubCommandResponse acceptClubRegistration(Long clubRegistrationId);

    void deleteClubRegistration(Long clubRegistrationId);

    // 동아리 기본 정보
    ClubCommandResponse updateClubBasicInformation(Long clubId, ClubBasicInformationUpdateRequest request,
                                                   MultipartFile file);

    void updateClubRecruitmentStatus(Long clubId, int status);
    // 동아리 상세 정보
    ClubCommandResponse saveClubDetail(Long clubId, ClubDetailRequest clubDetailDTO);

    ClubCommandResponse saveClubDetailDraft(Long clubId, ClubDetailRequest clubDetailDTO);

    // 동아리 월 별 일정
    ScheduleListResponse saveAndUpdateClubSchedule(Long clubId, ClubScheduleListRequest clubActivityDTO);

    ClubScheduleDraftResponse saveAndUpdateClubScheduleDraft(Long clubId,
                                                             ClubScheduleListRequest clubActivityDTO);

    void deleteClubSchedule(Long clubId, Long scheduleId);

    // 동아리 소개
    ClubCommandResponse saveClubIntroduction(Long clubId, ClubIntroductionRequest introduction);

    ClubCommandResponse saveClubIntroductionDraft(Long clubId, ClubIntroductionRequest introduction);

    // 동아리 모집 안내
    ClubCommandResponse saveClubRecruitment(Long clubId, ClubRecruitmentRequest recruitment);

    ClubCommandResponse saveClubRecruitmentDraft(Long clubId, ClubRecruitmentRequest recruitment);

    // 조회 수 증가
    void incrementClubViewCount(Long clubId);

}


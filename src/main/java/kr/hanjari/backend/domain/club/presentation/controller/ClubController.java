package kr.hanjari.backend.domain.club.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Objects;
import kr.hanjari.backend.domain.club.application.command.ClubCommandService;
import kr.hanjari.backend.domain.club.application.command.CodeGenerator;
import kr.hanjari.backend.domain.club.application.query.ClubQueryService;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubDetailRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubIntroductionRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubRecruitmentRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubScheduleListRequest;
import kr.hanjari.backend.domain.club.presentation.dto.response.*;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubBasicInfoResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubDetailDraftResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubIntroductionDraftResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubRecruitmentDraftResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubScheduleDraftResponse;
import kr.hanjari.backend.global.payload.ApiResponse;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubQueryService clubQueryService;
    private final ClubCommandService clubCommandService;
    private final CodeGenerator codeGenerator;

    /*------------------------ 동아리 등록 ----------------------------*/
    @Tag(name = "Club Registration", description = "Club Registration API")
    @Operation(summary = "[동아리 등록] 동아리 등록 요청", description = """
            ## 동아리 등록을 요청합니다.
            ### Request
            #### requestBody (JSON)
            - **clubName**: 동아리명
            - **leaderEmail**: 대표자 이메일(승인 관련 메일 받을 이메일)
            - **category**: 동아리 카테고리(SPORTS, ART, VOLUNTEER, UNION, ACADEMIC, RELIGION)
            - **oneLiner**: 동아리 한줄소개
            - **briefIntroduction**: 동아리 간단소개
            - **clubType**: 동아리 유형(CENTRAL, UNION, COLLEGE, DEPARTMENT)
            + 카테고리와 관련된 디테일한 내용은 슬랙을 참고해주세요.
            #### image (multipart/form-data)
            - **동아리 대표 이미지**
            ### Response
            - **생성된 ClubRegistration의 id**
            """)
    @PostMapping("/registrations")
    public ApiResponse<ClubCommandResponse> requestClubRegistration(
            @RequestPart ClubBasicInformationRequest requestBody,
            @RequestPart MultipartFile image) {
        requestBody.validate();
        ClubCommandResponse result = clubCommandService.requestClubRegistration(requestBody, image);
        return ApiResponse.onSuccess(result);
    }

    @Tag(name = "Club Registration", description = "Club Registration API")
    @Operation(summary = "[동아리 등록] 등록 요청 동아리 조회", description = """
            ## 등록 요청된 동아리를 조회합니다.
            ### Response
            - clubRegistrationDTOList: 등록 요청한 동아리 리스트
            """)
    @GetMapping("/service-admin/registrations")
    public ApiResponse<GetRegistrationsResponse> getAllClubRegistrations() {
        GetRegistrationsResponse result = clubQueryService.getRegistrations();

        return ApiResponse.onSuccess(result);
    }

    @Tag(name = "Club Registration", description = "Club Registration API")
    @Operation(summary = "[동아리 등록] 동아리 등록 요청 수락", description = """
            ## 동아리 등록 요청을 수락하여 동아리로 등록합니다.
            ### PathVariable
            - **clubRegistrationId**: 수락하려는 clubRegistration의 ID
            ### Response
            - **수락 후 새로 등록된 club의 id**
            """)
    @PostMapping("/service-admin/registrations/{clubRegistrationId}")
    public ApiResponse<ClubCommandResponse> acceptClubRegistration(@PathVariable Long clubRegistrationId) {

        ClubCommandResponse result = clubCommandService.acceptClubRegistration(clubRegistrationId);
        return ApiResponse.onSuccess(result);
    }

    @Tag(name = "Club Registration", description = "Club Registration API")
    @Operation(summary = "[동아리 등록] 동아리 등록 요청 삭제", description = """
            ## 동아리 등록 요청을 삭제합니다.
            ### PathVariable
            - **clubRegistrationId**: 삭제하려는 clubRegistration의 ID
            ### Response
            - 없음
            """)
    @DeleteMapping("/service-admin/registrations/{clubRegistrationId}")
    public ApiResponse<Void> deleteClubRegistration(@PathVariable Long clubRegistrationId) {

        clubCommandService.deleteClubRegistration(clubRegistrationId);

        return ApiResponse.onSuccess();
    }

    /*------------------------ 동아리 기본 ----------------------------*/
    @Tag(name = "Club Basic", description = "Club Basic API")
    @Operation(summary = "[동아리 기본] 동아리 기본 정보 수정", description = """
            ## 동아리 기본 정보를 수정합니다.
            ### RequestBody
            - **name**: 동아리명
            - **leaderEmail**: 대표자 이메일(승인 관련 메일 받을 이메일)
            - **category**: 동아리 카테고리(SPORTS, ART)
            - **oneLiner**: 동아리 한줄소개
            - **briefIntroduction**: 동아리 간단소개
            - **clubType**: 동아리 유형(CENTRAL, UNION, COLLEGE, DEPARTMENT)
            + 카테고리와 관련된 디테일한 내용은 슬랙을 참고해주세요.
            ### Multipart/form-data
            - **image**: 동아리 대표 사진
            """)
    @PostMapping("{clubId}/update")
    public ApiResponse<ClubCommandResponse> updateClubInfo(@RequestPart ClubBasicInformationRequest requestBody,
                                                           @RequestPart MultipartFile image,
                                                           @PathVariable Long clubId,
                                                           @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (!Objects.equals(customUserDetails.getClubId(), clubId)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        return ApiResponse.onSuccess(clubCommandService.updateClubBasicInformation(clubId, requestBody, image));
    }


    /*----------------------------- 동아리 상세 -----------------------------*/    // 조회
    @Tag(name = "Club Detail", description = "Club Detail API")
    @Operation(summary = "[동아리 정보] 동아리 상세 정보 조회", description = """
            ## 동아리 상세 정보를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}")
    public ApiResponse<ClubResponse> getSpecificClub(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubDetail(clubId));
    }

    // 조회
    @Tag(name = "Club Detail", description = "Club Detail API")
    @Operation(summary = "[동아리 정보] 동아리 오버뷰 정보 조회", description = """
            ## 동아리  오버뷰 정보를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/overview")
    public ApiResponse<ClubOverviewResponse> getSpecificClubOverview(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubOverview(clubId));
    }

    @Tag(name = "Club Detail", description = "Club Detail API")
    @Operation(summary = "[동아리 정보] 동아리 기본 정보 조회", description = """
            ## 동아리 기본 정보 를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/info")
    public ApiResponse<ClubBasicInfoResponse> getSpecificClubBasicInfo(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubBasicInfo(clubId));
    }

    @Tag(name = "Club Detail", description = "Club Detail API")
    @Operation(summary = "[동아리 상세] 동아리 상세 정보 입력 및 수정", description = """
            ## 동아리 상세 정보를 입력합니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **recruitmentStatus**: 동아리 모집 상태 (enum, {UPCOMING, OPEN, CLOSED}) \n
            - **leaderName**: 동아리 대표자 이름 (string) \n
            - **leaderPhone**: 동아리 대표자 연락처 (string) \n
            - **activities**: 정기 모임 일정 (string) \n
            - **membershipFee**: 회비 (integer) \n
            - **snsUrl**: SNS 링크 (string) \n
            - **applicationUrl**: 동아리 지원 링크 (string) \n
            """)
    @PostMapping("/club-admin/{clubId}")
    public ApiResponse<ClubCommandResponse> postSpecificClub(
            @PathVariable Long clubId,
            @RequestBody ClubDetailRequest clubDetailDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (!Objects.equals(customUserDetails.getClubId(), clubId)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        return ApiResponse.onSuccess(clubCommandService.saveClubDetail(clubId, clubDetailDTO));
    }

    @Tag(name = "Club Detail", description = "Club Detail API")
    @Operation(summary = "[동아리 상세] 임시 저장된 동아리 상세 정보 조회", description = """
            ## 동아리 상세 정보를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            
            """)
    @GetMapping("/{clubId}/draft")
    public ApiResponse<ClubDetailDraftResponse> getSpecificClubDraft(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubDetailDraft(clubId));
    }

    @Tag(name = "Club Detail", description = "Club Detail API")
    @Operation(summary = "[동아리 상세] 동아리 상세 정보 임시저장", description = """
            ## 동아리 상세 정보를 임시저장합니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **recruitmentStatus**: 동아리 모집 상태 (enum, {UPCOMING, OPEN, CLOSED}) \n
            - **leaderName**: 동아리 대표자 이름 (string) \n
            - **leaderPhone**: 동아리 대표자 연락처 (string) \n
            - **activities**: 정기 모임 일정 (string) \n
            - **membershipFee**: 회비 (integer) \n
            - **snsUrl**: SNS 링크 (string) \n
            - **applicationUrl**: 동아리 지원 링크 (string) \n
            """)
    @PostMapping("/club-admin/{clubId}/draft")
    public ApiResponse<ClubCommandResponse> postSpecificClubDraft(
            @PathVariable Long clubId,
            @RequestBody ClubDetailRequest clubDetailDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveClubDetailDraft(clubId, clubDetailDTO));
    }

    /*--------------------------- 동아리 월 별 일정 ---------------------------*/
    @Tag(name = "Club Intro - Schedules", description = "Club Intro - Schedules API")
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정", description = """
            ## 동아리 월 별 일정을 전체 조회합니다.
            ### Path Variable
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/schedules")
    public ApiResponse<ClubScheduleResponse> getClubSchedules(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findAllClubActivities(clubId));
    }

    @Tag(name = "Club Intro - Schedules", description = "Club Intro - Schedules API")
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정 입력 및 수정", description = """
            ## 동아리 월 별 일정을 입력 및 수정합니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **month**: 월 (integer, 1~12 사이) \n
            - **content**: 일정 내용 (string, 30자 미만) \n
            - **scheduleId**: 일정 ID (수정 시에만 필요)
            """)
    @PostMapping("/club-admin/{clubId}/schedules")
    public ApiResponse<ScheduleListResponse> postClubSchedules(
            @PathVariable Long clubId,
            @RequestBody ClubScheduleListRequest clubActivityDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (!Objects.equals(customUserDetails.getClubId(), clubId)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        return ApiResponse.onSuccess(clubCommandService.saveAndUpdateClubSchedule(clubId, clubActivityDTO));
    }

    @Tag(name = "Club Intro - Schedules", description = "Club Intro - Schedules API")
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정 삭제", description = """
            ## 동아리 월 별 일정을 삭제합니다. 
            ### Path Variable
            - **clubId**: 삭제할 동아리의 ID
            - **scheduleId**: 삭제할 일정의 ID
            """)
    @DeleteMapping("/club-admin/{clubId}/schedules/{scheduleId}")
    public ApiResponse<Void> deleteClubSchedules(
            @PathVariable Long clubId,
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (!Objects.equals(customUserDetails.getClubId(), clubId)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        clubCommandService.deleteClubSchedule(clubId, scheduleId);
        return ApiResponse.onSuccess();
    }

    @Tag(name = "Club Intro - Schedules", description = "Club Intro - Schedules API")
    @Operation(summary = "[동아리 소개] 임시 저장된 동아리 월 별 일정 조회", description = """
            ## 임시 저장 된 동아리 월 별 일정을 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/club-admin/{clubId}/schedules/draft")
    public ApiResponse<ClubScheduleDraftResponse> getClubSchedulesDraft(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findAllClubActivitiesDraft(clubId));
    }

    @Tag(name = "Club Intro - Schedules", description = "Club Intro - Schedules API")
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정 임시 저장", description = """
            ## 동아리 월 별 일정을 임시 저장합니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **month**: 월 (integer, 1~12 사이) \n
            - **content**: 일정 내용 (string, 30자 미만) \n
            - **scheduleId**: 일정 ID (수정 시에만 필요)
            """)
    @PostMapping("/club-admin/{clubId}/schedules/draft")
    public ApiResponse<ClubScheduleDraftResponse> postClubSchedulesDraft(
            @PathVariable Long clubId,
            @RequestBody ClubScheduleListRequest clubActivityDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveAndUpdateClubScheduleDraft(clubId, clubActivityDTO));
    }


    /*----------------------------- 동아리 소개글 ------------------------------*/
    @Tag(name = "Club Intro - Introduction", description = "Club Intro - Introduction API")
    @Operation(summary = "[동아리 소개] 동아리 소개 조회", description = """
            ## 동아리 소개글을 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/introduction")
    public ApiResponse<ClubIntroductionResponse> getClubIntroduction(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubIntroduction(clubId));
    }

    @Tag(name = "Club Intro - Introduction", description = "Club Intro - Introduction API")
    @Operation(summary = "[동아리 소개] 동아리 소개 입력 및 수정", description = """
            ## 동아리 소개글을 입력 및 수정합니다. 입력된 동아리 소개글이 없을 경우 새로 생성됩니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **introduction**: 동아리 소개 (string, 500자 미만) \n
            - **activity**: 활동 내용 (string, 1000자 미만) \n
            - **recruitment**: 원하는 동아리 원 설명 (string, 500자 미만) \n
            """)
    @PostMapping("/club-admin/{clubId}/introduction")
    public ApiResponse<ClubCommandResponse> postClubIntroduction(
            @PathVariable Long clubId,
            @RequestBody ClubIntroductionRequest clubIntroductionDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (!Objects.equals(customUserDetails.getClubId(), clubId)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        return ApiResponse.onSuccess(clubCommandService.saveClubIntroduction(clubId, clubIntroductionDTO));
    }

    @Tag(name = "Club Intro - Introduction", description = "Club Intro - Introduction API")
    @Operation(summary = "[동아리 소개] 임시 저장된 동아리 소개 조회", description = """
            ## 임시 저장 된 동아리 소개글을 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/club-admin/{clubId}/introduction/draft")
    public ApiResponse<ClubIntroductionDraftResponse> getClubIntroductionDraft(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubIntroductionDraft(clubId));
    }

    @Tag(name = "Club Intro - Introduction", description = "Club Intro - Introduction API")
    @Operation(summary = "[동아리 소개] 동아리 소개 임시 저장", description = """
            ## 동아리 소개글을 임시 저장 합니다. 
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **introduction**: 동아리 소개 (string, 500자 미만) \n
            - **activity**: 활동 내용 (string, 1000자 미만) \n
            - **recruitment**: 원하는 동아리 원 설명 (string, 500자 미만) \n
            """)
    @PostMapping("/club-admin/{clubId}/introduction/draft")
    public ApiResponse<ClubCommandResponse> postClubIntroductionDraft(
            @PathVariable Long clubId,
            @RequestBody ClubIntroductionRequest clubIntroductionDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveClubIntroductionDraft(clubId, clubIntroductionDTO));
    }


    /*----------------------------- 동아리 모집 안내 ------------------------------*/
    @Tag(name = "Club Intro - Recruitment", description = "Club Intro - Recruitment API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 조회", description = """
            ## 동아리 모집 안내를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/recruitment")
    public ApiResponse<ClubRecruitmentResponse> getClubRecruitment(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubRecruitment(clubId));
    }

    @Tag(name = "Club Intro - Recruitment", description = "Club Intro - Recruitment API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 입력 및 수정", description = """
            ## 동아리 모집 안내를 입력 및 수정합니다. 입력된 동아리 모집 안내가 없을 경우 새로 생성됩니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **due**: 동아리 모집 기간 (string, 500자 미만) \n
            - **notice**: 유의사항 (string, 500자 미만) \n
            - **etc**: 기타 동아리 모집 안내 (string, 500자 미만) \n
            """)
    @PostMapping("/club-admin/{clubId}/recruitment")
    public ApiResponse<ClubCommandResponse> postClubRecruitment(
            @PathVariable Long clubId,
            @RequestBody ClubRecruitmentRequest clubRecruitmentDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (!Objects.equals(customUserDetails.getClubId(), clubId)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        return ApiResponse.onSuccess(clubCommandService.saveClubRecruitment(clubId, clubRecruitmentDTO));
    }

    @Tag(name = "Club Intro - Recruitment", description = "Club Intro - Recruitment API")
    @Operation(summary = "[동아리 모집 안내] 임시 저장 된 동아리 모집 안내 조회", description = """
            ## 임시 저장 된 동아리 모집 안내를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/club-admin/{clubId}/recruitment/draft")
    public ApiResponse<ClubRecruitmentDraftResponse> getClubRecruitmentDraft(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubRecruitmentDraft(clubId));
    }

    @Tag(name = "Club Intro - Recruitment", description = "Club Intro - Recruitment API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 임시 저장", description = """
            ## 동아리 모집 안내를 임시저장 합니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **due**: 동아리 모집 기간 (string, 500자 미만) \n
            - **notice**: 유의사항 (string, 500자 미만) \n
            - **etc**: 기타 동아리 모집 안내 (string, 500자 미만) \n
            """)
    @PostMapping("/club-admin/{clubId}/recruitment/draft")
    public ApiResponse<ClubCommandResponse> postClubRecruitmentDraft(
            @PathVariable Long clubId,
            @RequestBody ClubRecruitmentRequest clubRecruitmentDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveClubRecruitmentDraft(clubId, clubRecruitmentDTO));
    }

    @Tag(name = "Club Verification Code", description = "Club Verification Code API")
    @Operation(summary = "[인증] 동아리 인증 코드 재발급", description = """
            ## 동아리의 인증 코드를 재발급합니다(기존 인증 코드 대체).
            - **clubId**: 동아리 ID
            """)
    @PostMapping("/service-admin/reissue")
    public ApiResponse<ClubCodeResponse> reissueClubCode(@RequestParam Long clubId) {
        return ApiResponse.onSuccess(ClubCodeResponse.of(codeGenerator.reissueCode(clubId)));
    }

}

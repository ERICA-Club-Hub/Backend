package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Objects;
import kr.hanjari.backend.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.enums.SortBy;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.security.detail.CustomUserDetails;
import kr.hanjari.backend.service.club.ClubCommandService;
import kr.hanjari.backend.service.club.ClubQueryService;
import kr.hanjari.backend.service.club.ClubUtil;
import kr.hanjari.backend.web.dto.club.request.ClubBasicInformationDTO;
import kr.hanjari.backend.web.dto.club.request.ClubDetailRequestDTO;
import kr.hanjari.backend.web.dto.club.request.ClubIntroductionRequestDTO;
import kr.hanjari.backend.web.dto.club.request.ClubRecruitmentRequestDTO;
import kr.hanjari.backend.web.dto.club.request.ClubScheduleListRequestDTO;
import kr.hanjari.backend.web.dto.club.response.ClubDetailListResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubIntroductionResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubOverviewResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubRecruitmentResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubScheduleResponseDTO;
import kr.hanjari.backend.web.dto.club.response.GetRegistrationsResponseDTO;
import kr.hanjari.backend.web.dto.club.response.draft.ClubBasicInfoResponseDTO;
import kr.hanjari.backend.web.dto.club.response.draft.ClubDetailDraftResponseDTO;
import kr.hanjari.backend.web.dto.club.response.draft.ClubIntroductionDraftResponseDTO;
import kr.hanjari.backend.web.dto.club.response.draft.ClubRecruitmentDraftResponseDTO;
import kr.hanjari.backend.web.dto.club.response.draft.ClubScheduleDraftResponseDTO;
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
    private final ClubUtil clubUtil;

    /*------------------------ 동아리 등록 ----------------------------*/
    @Tag(name = "동아리 등록", description = "동아리 등록 관련 API")
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
    public ApiResponse<Long> requestClubRegistration(@RequestPart ClubBasicInformationDTO requestBody,
                                                     @RequestPart MultipartFile image) {
        requestBody.validate();

        Long result = clubCommandService.requestClubRegistration(requestBody, image);
        return ApiResponse.onSuccess(result);
    }

    @Tag(name = "동아리 등록", description = "동아리 등록 관련 API")
    @Operation(summary = "[동아리 등록] 등록 요청 동아리 조회", description = """
            ## 등록 요청된 동아리를 조회합니다.
            ### Response
            - clubRegistrationDTOList: 등록 요청한 동아리 리스트
            """)
    @GetMapping("/service-admin/registrations")
    public ApiResponse<GetRegistrationsResponseDTO> getAllClubRegistrations() {
        GetRegistrationsResponseDTO result = clubQueryService.getRegistrations();

        return ApiResponse.onSuccess(result);
    }

    @Tag(name = "동아리 등록", description = "동아리 등록 관련 API")
    @Operation(summary = "[동아리 등록] 동아리 등록 요청 수락", description = """
            ## 동아리 등록 요청을 수락하여 동아리로 등록합니다.
            ### PathVariable
            - **clubRegistrationId**: 수락하려는 clubRegistration의 ID
            ### Response
            - **수락 후 새로 등록된 club의 id**
            """)
    @PostMapping("/service-admin/registrations/{clubRegistrationId}")
    public ApiResponse<Long> acceptClubRegistration(@PathVariable Long clubRegistrationId) {

        Long result = clubCommandService.acceptClubRegistration(clubRegistrationId);
        return ApiResponse.onSuccess(result);
    }

    /*------------------------ 동아리 기본 ----------------------------*/
    @Tag(name = "동아리 기본", description = "동아리 기본 API")
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
    public ApiResponse<Long> updateClubInfo(@RequestPart ClubBasicInformationDTO requestBody,
                                            @RequestPart MultipartFile image,
                                            @PathVariable Long clubId,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (!Objects.equals(customUserDetails.getClubId(), clubId)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        return ApiResponse.onSuccess(clubCommandService.updateClubBasicInformation(clubId, requestBody, image));
    }

    /*------------------------ 동아리 조건 별 조회 ----------------------------*/
    @Tag(name = "동아리 검색", description = "동아리 검색 관련 API")
    @Operation(summary = "[동아리 검색] 동아리 검색", description = """
            ## 입력한 조건에 맞는 동아리를 검색합니다. 
            - **keyword**: 동아리 이름에서 서 검색할 키워드 \n
            - **category**: 동아리 카테고리 \n
            - **status**: 동아리 모집 상태 \n
            - **sortBy**: 정렬 기준 \n
            
            ### 모든 조건은 선택적으로 입력할 수 있습니다. (필수 X)
            아무 값도 입력 하지 않을 경우, 가나다순으로 정렬하여 전체 동아리를 조회합니다. page의 기본 값은 0, size의 기본 값은 10입니다.
            """)
    @GetMapping("")
    public ApiResponse<ClubDetailListResponseDTO> getClubsByCondition(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CentralClubCategory category,
            @RequestParam(required = false) RecruitmentStatus status,
            @RequestParam(required = false) SortBy sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(
                clubQueryService.findClubsByCondition(keyword, category, status, sortBy, page, size));
    }

    /*----------------------------- 동아리 상세 -----------------------------*/    // 조회
    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
    @Operation(summary = "[동아리 정보] 동아리 상세 정보 조회", description = """
            ## 동아리 상세 정보를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}")
    public ApiResponse<ClubResponseDTO> getSpecificClub(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubDetail(clubId));
    }

    // 조회
    @Tag(name = "동아리 상세", description = "동아리 상세 v2 - 동아리 오버뷰 정보 API")
    @Operation(summary = "[동아리 정보] 동아리 오버뷰 정보 조회", description = """
            ## 동아리  오버뷰 정보를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/overview")
    public ApiResponse<ClubOverviewResponseDTO> getSpecificClubOverview(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubOverview(clubId));
    }

    @Tag(name = "동아리 상세", description = "동아리 상세 v2 - 동아리 기본 정보 API")
    @Operation(summary = "[동아리 정보] 동아리 기본 정보 조회", description = """
            ## 동아리 기본 정보 를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/info")
    public ApiResponse<ClubBasicInfoResponseDTO> getSpecificClubBasicInfo(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubBasicInfo(clubId));
    }

    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
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
    public ApiResponse<Long> postSpecificClub(
            @PathVariable Long clubId,
            @RequestBody ClubDetailRequestDTO clubDetailDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (!Objects.equals(customUserDetails.getClubId(), clubId)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        return ApiResponse.onSuccess(clubCommandService.saveClubDetail(clubId, clubDetailDTO));
    }

    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
    @Operation(summary = "[동아리 상세] 임시 저장된 동아리 상세 정보 조회", description = """
            ## 동아리 상세 정보를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            
            """)
    @GetMapping("/{clubId}/draft")
    public ApiResponse<ClubDetailDraftResponseDTO> getSpecificClubDraft(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubDetailDraft(clubId));
    }

    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
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
    public ApiResponse<Long> postSpecificClubDraft(
            @PathVariable Long clubId,
            @RequestBody ClubDetailRequestDTO clubDetailDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveClubDetailDraft(clubId, clubDetailDTO));
    }

    /*--------------------------- 동아리 월 별 일정 ---------------------------*/
    @Tag(name = "동아리 소개 - 월 별 일정", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정", description = """
            ## 동아리 월 별 일정을 전체 조회합니다.
            ### Path Variable
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/schedules")
    public ApiResponse<ClubScheduleResponseDTO> getClubSchedules(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findAllClubActivities(clubId));
    }

    @Tag(name = "동아리 소개 - 월 별 일정", description = "동아리 소개 관련 API")
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
    public ApiResponse<?> postClubSchedules(
            @PathVariable Long clubId,
            @RequestBody ClubScheduleListRequestDTO clubActivityDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (!Objects.equals(customUserDetails.getClubId(), clubId)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        return ApiResponse.onSuccess(clubCommandService.saveAndUpdateClubSchedule(clubId, clubActivityDTO));
    }

    @Tag(name = "동아리 소개 - 월 별 일정", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정 삭제", description = """
            ## 동아리 월 별 일정을 삭제합니다. 
            ### Path Variable
            - **clubId**: 삭제할 동아리의 ID
            - **scheduleId**: 삭제할 일정의 ID
            """)
    @DeleteMapping("/club-admin/{clubId}/schedules/{scheduleId}")
    public ApiResponse<?> deleteClubSchedules(
            @PathVariable Long clubId,
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (!Objects.equals(customUserDetails.getClubId(), clubId)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        clubCommandService.deleteClubSchedule(clubId, scheduleId);
        return ApiResponse.onSuccess();
    }

    @Tag(name = "동아리 소개 - 월 별 일정", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 임시 저장된 동아리 월 별 일정 조회", description = """
            ## 임시 저장 된 동아리 월 별 일정을 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/club-admin/{clubId}/schedules/draft")
    public ApiResponse<ClubScheduleDraftResponseDTO> getClubSchedulesDraft(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findAllClubActivitiesDraft(clubId));
    }

    @Tag(name = "동아리 소개 - 월 별 일정", description = "동아리 소개 관련 API")
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
    public ApiResponse<?> postClubSchedulesDraft(
            @PathVariable Long clubId,
            @RequestBody ClubScheduleListRequestDTO clubActivityDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveAndUpdateClubScheduleDraft(clubId, clubActivityDTO));
    }


    /*----------------------------- 동아리 소개글 ------------------------------*/
    @Tag(name = "동아리 소개 - 소개글", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 소개 조회", description = """
            ## 동아리 소개글을 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/introduction")
    public ApiResponse<ClubIntroductionResponseDTO> getClubIntroduction(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubIntroduction(clubId));
    }

    @Tag(name = "동아리 소개 - 소개글", description = "동아리 소개 관련 API")
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
    public ApiResponse<?> postClubIntroduction(
            @PathVariable Long clubId,
            @RequestBody ClubIntroductionRequestDTO clubIntroductionDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (!Objects.equals(customUserDetails.getClubId(), clubId)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        return ApiResponse.onSuccess(clubCommandService.saveClubIntroduction(clubId, clubIntroductionDTO));
    }

    @Tag(name = "동아리 소개 - 소개글", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 임시 저장된 동아리 소개 조회", description = """
            ## 임시 저장 된 동아리 소개글을 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/club-admin/{clubId}/introduction/draft")
    public ApiResponse<ClubIntroductionDraftResponseDTO> getClubIntroductionDraft(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubIntroductionDraft(clubId));
    }

    @Tag(name = "동아리 소개 - 소개글", description = "동아리 소개 관련 API")
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
    public ApiResponse<?> postClubIntroductionDraft(
            @PathVariable Long clubId,
            @RequestBody ClubIntroductionRequestDTO clubIntroductionDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveClubIntroductionDraft(clubId, clubIntroductionDTO));
    }


    /*----------------------------- 동아리 모집 안내 ------------------------------*/
    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 조회", description = """
            ## 동아리 모집 안내를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/recruitment")
    public ApiResponse<ClubRecruitmentResponseDTO> getClubRecruitment(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubRecruitment(clubId));
    }

    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
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
    public ApiResponse<?> postClubRecruitment(
            @PathVariable Long clubId,
            @RequestBody ClubRecruitmentRequestDTO clubRecruitmentDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (!Objects.equals(customUserDetails.getClubId(), clubId)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        return ApiResponse.onSuccess(clubCommandService.saveClubRecruitment(clubId, clubRecruitmentDTO));
    }

    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
    @Operation(summary = "[동아리 모집 안내] 임시 저장 된 동아리 모집 안내 조회", description = """
            ## 임시 저장 된 동아리 모집 안내를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/club-admin/{clubId}/recruitment/draft")
    public ApiResponse<ClubRecruitmentDraftResponseDTO> getClubRecruitmentDraft(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubRecruitmentDraft(clubId));
    }

    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
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
    public ApiResponse<?> postClubRecruitmentDraft(
            @PathVariable Long clubId,
            @RequestBody ClubRecruitmentRequestDTO clubRecruitmentDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveClubRecruitmentDraft(clubId, clubRecruitmentDTO));
    }

    @Tag(name = "동아리 인증 코드", description = "동아리 인증 코드 발급 API")
    @Operation(summary = "[인증] 동아리 인증 코드 재발급", description = """
            ## 동아리의 인증 코드를 재발급합니다(기존 인증 코드 대체).
            - **clubId**: 동아리 ID
            """)
    @PostMapping("/service-admin/reissue")
    public ApiResponse<String> reissueClubCode(@RequestParam Long clubId) {

        String result = clubUtil.reissueClubCode(clubId);
        return ApiResponse.onSuccess(result);
    }
}

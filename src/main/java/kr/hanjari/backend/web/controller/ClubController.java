package kr.hanjari.backend.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.domain.enums.ClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.enums.SortBy;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.service.club.ClubCommandService;
import kr.hanjari.backend.service.club.ClubQueryService;
import kr.hanjari.backend.web.dto.club.request.ClubDetailRequestDTO;
import kr.hanjari.backend.web.dto.club.request.ClubIntroductionRequestDTO;
import kr.hanjari.backend.web.dto.club.request.ClubRecruitmentRequestDTO;
import kr.hanjari.backend.web.dto.club.request.ClubScheduleRequestDTO;
import kr.hanjari.backend.web.dto.club.request.CommonClubDTO;
import kr.hanjari.backend.web.dto.club.response.ClubRecruitmentResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubIntroductionResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubScheduleResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubSearchResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RequestMapping("/api/clubs")
@RestController
@RequiredArgsConstructor
public class ClubController {

    private final ClubQueryService clubQueryService;
    private final ClubCommandService clubCommandService;

    @Tag(name = "동아리 등록", description = "동아리 등록 관련 API")
    @Operation(summary = "[동아리 등록] 동아리 등록 요청", description = """
            ## 동아리 등록을 요청합니다.
            ### Request
            #### requestBody (JSON)
            - **clubName**: 동아리명
            - **leaderEmail**: 대표자 이메일(승인 관련 메일 받을 이메일)
            - **category**: 동아리 카테고리(SPORTS, ART)
            - **oneLiner**: 동아리 한줄소개
            - **briefIntroduction**: 동아리 간단소개
            #### image (multipart/form-data)
            - **동아리 대표 이미지**
            ### Response
            - **생성된 ClubRegistration의 id**
            """)
    @PostMapping("/registrations")
    public ApiResponse<Long> requestClubRegistration(@RequestPart CommonClubDTO requestBody,
                                                     @RequestPart MultipartFile image) {

        Long result = clubCommandService.requestClubRegistration(requestBody, image);
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
    @PostMapping("/registrations/{clubRegistrationId}")
    public ApiResponse<Long> acceptClubRegistration(@PathVariable Long clubRegistrationId) {

        Long result = clubCommandService.acceptClubRegistration(clubRegistrationId);
        return ApiResponse.onSuccess(result);
    }

    @Tag(name = "동아리 기본", description = "동아리 기본 API")
    @Operation(summary = "[동아리 기본] 동아리 기본 정보 수정", description = """
            ## 동아리 기본 정보를 수정합니다.
            ### RequestBody
            - **name**: 동아리명
            - **leaderEmail**: 대표자 이메일(승인 관련 메일 받을 이메일)
            - **category**: 동아리 카테고리(SPORTS, ART)
            - **oneLiner**: 동아리 한줄소개
            - **briefIntroduction**: 동아리 간단소개
            ### Multipart/form-data
            - **image**: 동아리 대표 사진
            """)
    public void updateClubInfo(@RequestPart CommonClubDTO request,
                               @RequestPart MultipartFile image) {
        return;
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
    public ApiResponse<ClubSearchResponseDTO> getClubsByCondition(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) ClubCategory category,
            @RequestParam(required = false) RecruitmentStatus status,
            @RequestParam(required = false) SortBy sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(clubQueryService.findClubsByCondition(keyword, category, status, sortBy, page, size));
    }

    /*----------------------------- 동아리 상세 -----------------------------*/
    // 조회
    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
    @Operation(summary = "[동아리 상세] 동아리 상세 정보 조회", description = """
            ## 동아리 상세 정보를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}")
    public ApiResponse<ClubResponseDTO> getSpecificClub(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubQueryService.findClubDetail(clubId));
    }

    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
    @Operation(summary = "[동아리 상세] 동아리 상세 정보 입력 및 수정", description = """
            ## 동아리 상세 정보를 입력합니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **recruitmentStatus**: 동아리 모집 상태 (enum, {UPCOMING, OPEN, CLOSED}) \n
            - **leaderName**: 동아리 대표자 이름 (string) \n
            - **leaderEmail**: 동아리 대표자 이메일 (string) \n
            - **leaderPhone**: 동아리 대표자 연락처 (string) \n
            - **activities**: 정기 모임 일정 (string) \n
            - **snsUrl**: SNS 링크 (string) \n
            - **applicationUrl**: 동아리 지원 링크 (string) \n
            """)
    @PostMapping("/{clubId}")
    public ApiResponse<Long> postSpecificClub(
            @PathVariable Long clubId,
            @RequestBody ClubDetailRequestDTO clubDetailDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveClubDetail(clubId, clubDetailDTO));
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
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정 입력", description = """
            ## 동아리 월 별 일정을 입력합니다. 
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **month**: 월 (integer, 1~12 사이) \n
            - **content**: 활동 내용 (string, 30자 미만) \n
            """)
    @PostMapping("/{clubId}/schedules")
    public ApiResponse<?> postClubSchedules(
            @PathVariable Long clubId,
            @RequestBody ClubScheduleRequestDTO clubActivityDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveClubSchedule(clubId, clubActivityDTO));
    }

    @Tag(name = "동아리 소개 - 월 별 일정", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정 수정", description = """
            ## 동아리 월 별 일정을 수정합니다. 
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            - **scheduleId**: 수정할 활동의 ID  \n
            
            ### Request Body
            - **month**: 변경 하고싶은 월 (어떤 월로 바꾸고 싶은지 입력) (integer) \n
            - **content**: 활동 내용 (string, 30자 미만) \n
            """)
    @PatchMapping("/{clubId}/schedules/{scheduleId}")
    public ApiResponse<?> patchClubSchedules(
            @PathVariable Long clubId,
            @PathVariable Long scheduleId,
            @RequestBody ClubScheduleRequestDTO clubScheduleDTO) {
        return ApiResponse.onSuccess(clubCommandService.updateClubSchedule(clubId, scheduleId, clubScheduleDTO));
    }

    @Tag(name = "동아리 소개 - 월 별 일정", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정 삭제", description = """
            ## 동아리 월 별 일정을 삭제합니다. 
            ### Path Variable
            - **clubId**: 삭제할 동아리의 ID
            - **scheduleId**: 삭제할 활동의 ID
            """)
    @DeleteMapping("/{clubId}/schedules/{scheduleId}")
    public ApiResponse<?> deleteClubSchedules(
            @PathVariable Long clubId,
            @PathVariable Long scheduleId) {
        clubCommandService.deleteClubSchedule(clubId, scheduleId);
        return ApiResponse.onSuccess();
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
    @PostMapping("/{clubId}/introduction")
    public ApiResponse<?> postClubIntroduction(
            @PathVariable Long clubId,
            @RequestBody ClubIntroductionRequestDTO clubIntroductionDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveClubIntroduction(clubId, clubIntroductionDTO));
    }

    @Tag(name = "동아리 소개 - 소개글", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 임시 저장된 동아리 소개 조회", description = """
            ## 임시 저장 된 동아리 소개글을 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/introduction/draft")
    public ApiResponse<ClubIntroductionResponseDTO> getClubIntroductionDraft(@PathVariable Long clubId) {
        return ApiResponse.onSuccess();
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
    @PostMapping("/{clubId}/introduction/draft")
    public ApiResponse<?> postClubIntroductionDraft(
            @PathVariable Long clubId,
            @RequestBody ClubIntroductionRequestDTO clubIntroductionDTO) {
        return ApiResponse.onSuccess();
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
    @PostMapping("/{clubId}/recruitment")
    public ApiResponse<?> postClubRecruitment(
            @PathVariable Long clubId,
            @RequestBody ClubRecruitmentRequestDTO clubRecruitmentDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveClubRecruitment(clubId, clubRecruitmentDTO));
    }

    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
    @Operation(summary = "[동아리 모집 안내] 임시 저장 된 동아리 모집 안내 조회", description = """
            ## 임시 저장 된 동아리 모집 안내를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/recruitment/draft")
    public ApiResponse<ClubRecruitmentResponseDTO> getClubRecruitmentDraft(@PathVariable Long clubId) {
        return ApiResponse.onSuccess();
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
    @PostMapping("/{clubId}/recruitment/draft")
    public ApiResponse<?> postClubRecruitmentDraft(
            @PathVariable Long clubId,
            @RequestBody ClubRecruitmentRequestDTO clubRecruitmentDTO) {
        return ApiResponse.onSuccess();
    }
}

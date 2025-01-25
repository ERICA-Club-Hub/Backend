package kr.hanjari.backend.web.controller;

import static kr.hanjari.backend.web.dto.club.ClubResponseDTO.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.service.club.ClubCommandService;
import kr.hanjari.backend.service.club.ClubQueryService;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO;
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

    @Tag(name = "동아리 기본", description = "동아리 기본 API")
    @Operation(summary = "[동아리 기본] 동아리 등록 요청", description = """
            ## 동아리 등록을 요청합니다.
            ### RequestBody
            - **name**: 동아리명
            - **leaderEmail**: 대표자 이메일(승인 관련 메일 받을 이메일)
            - **category**: 동아리 카테고리(SPORTS, ART)
            - **oneLiner**: 동아리 한줄소개
            - **briefIntroduction**: 동아리 간단소개
            ### Multipart/form-data
            - **image**: 동아리 대표 사진
            """)
    @PostMapping("/")
    public void createNewClub(@RequestPart ClubRequestDTO.CommonClubDTO request,
                              @RequestPart MultipartFile image) {
        return;
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
    public void updateClubInfo(@RequestPart ClubRequestDTO.CommonClubDTO request,
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
            """)
    @GetMapping("")
    public ApiResponse<ClubSearchDTO> getClubsByCondition(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sortBy
    ) {
        return null;
    }

    /*----------------------------- 동아리 상세 -----------------------------*/
    // 조회
    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
    @Operation(summary = "[동아리 상세] 동아리 상세 정보 조회", description = """
            ## 동아리 상세 정보를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}")
    public ApiResponse<ClubDTO> getSpecificClub(@PathVariable Long clubId) {
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
            @RequestBody ClubRequestDTO.ClubDetailDTO clubDetailDTO) {
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
    public ApiResponse<ClubScheduleDTO> getClubSchedules(@PathVariable Long clubId) {
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
            @RequestBody ClubRequestDTO.ClubScheduleDTO clubActivityDTO) {
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
            @RequestBody ClubRequestDTO.ClubScheduleDTO clubScheduleDTO) {
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
    public ApiResponse<ClubIntroductionDTO> getClubIntroduction(@PathVariable Long clubId) {
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
            @RequestBody ClubRequestDTO.ClubIntroductionDTO clubIntroductionDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveClubIntroduction(clubId, clubIntroductionDTO));
    }

    /*----------------------------- 동아리 모집 안내 ------------------------------*/
    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 조회", description = """
            ## 동아리 모집 안내를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/recruitment")
    public ApiResponse<ClubRecruitmentDTO> getClubRecruitment(@PathVariable Long clubId) {
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
            @RequestBody ClubRequestDTO.ClubRecruitmentDTO clubRecruitmentDTO) {
        return ApiResponse.onSuccess(clubCommandService.saveClubRecruitment(clubId, clubRecruitmentDTO));
    }
}

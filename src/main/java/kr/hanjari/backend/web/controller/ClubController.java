package kr.hanjari.backend.web.controller;

import static kr.hanjari.backend.web.dto.club.ClubResponseDTO.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequestMapping("/api/clubs")
@RestController
@RequiredArgsConstructor
public class ClubController {

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
    public ApiResponse<ClubDetailDTO> getSpecificClub(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
    @Operation(summary = "[동아리 상세] 동아리 상세 정보 입력", description = """
            ## 동아리 상세 정보를 입력합니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **name**: 동아리 대표자 이름 (string) \n
            - **phone**: 동아리 대표자 연락처 (string) \n
            - **activities**: 정기 모임 일정 (string) \n
            - **sns**: SNS 링크 (string) \n
            """)
    @PostMapping("/{clubId}")
    public ApiResponse<?> postSpecificClub() {
        return null;
    }

    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
    @Operation(summary = "[동아리 상세] 동아리 상세 정보 수정", description = """
            ## 동아리 상세 정보를 수정합니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **name**: 동아리 대표자 이름 (string) \n
            - **phone**: 동아리 대표자 연락처 (string) \n
            - **activities**: 정기 모임 일정 (string) \n
            - **sns**: SNS 링크 (string) \n
            """)
    @PatchMapping("/{clubId}")
    public ApiResponse<?> patchSpecificClub(@PathVariable Long clubId) {
        return null;
    }


    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
    @Operation(summary = "[동아리 상세] 동아리 상세 정보 삭제", description = """
            ## 동아리 상세 정보를 삭제합니다.
            - **clubId**: 삭제할 동아리의 ID
            """)
    @DeleteMapping("/{clubId}")
    public ApiResponse<?> deleteSpecificClub(@PathVariable Long clubId) {
        return null;
    }

    /*--------------------------- 동아리 월 별 일정 ---------------------------*/
    @Tag(name = "동아리 소개 - 월 별 일정", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정", description = """
            ## 동아리 월 별 일정을 전체 조회합니다. 
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/schedules")
    public ApiResponse<ClubActivityDTO> getClubSchedules(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 소개 - 월 별 일정", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정 입력", description = """
            ## 동아리 월 별 일정을 입력합니다. 
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **month**: 월 (integer, 1~12 사이) \n
            - **activity**: 활동 내용 (string, 30자 미만) \n
            """)
    @PostMapping("/{clubId}/schedules")
    public ApiResponse<?> postClubSchedules(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 소개 - 월 별 일정", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정 수정", description = """
            ## 동아리 월 별 일정을 수정합니다. 
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **month**: 월 (integer) \n
            - **activity**: 활동 내용 (string, 30자 미만) \n
            """)
    @PatchMapping("/{clubId}/schedules")
    public ApiResponse<?> patchClubSchedules(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 소개 - 월 별 일정", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 월 별 일정 삭제", description = """
            ## 동아리 월 별 일정을 삭제합니다. 
            - **clubId**: 삭제할 동아리의 ID
            """)
    @DeleteMapping("/{clubId}/schedules")
    public ApiResponse<?> deleteClubSchedules(@PathVariable Long clubId) {
        return null;
    }

    /*----------------------------- 동아리 소개글 ------------------------------*/
    @Tag(name = "동아리 소개 - 소개글", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 소개 조회", description = """
            ## 동아리 소개글을 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/introduction")
    public ApiResponse<ClubIntroductionDTO> getClubIntroduction(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 소개 - 소개글", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 소개 입력", description = """
            ## 동아리 소개글을 입력합니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **introduction**: 동아리 소개 (string, 500자 미만) \n
            - **activity**: 활동 내용 (string, 1000자 미만) \n
            - **recruitment**: 원하는 동아리 원 설명 (string, 500자 미만) \n
            """)
    @PostMapping("/{clubId}/introduction")
    public ApiResponse<?> postClubIntroduction(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 소개 - 소개글", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 소개 수정", description = """
            ## 동아리 소개를 수정합니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **introduction**: 동아리 소개 (string, 500자 미만) \n
            - **activity**: 활동 내용 (string, 1000자 미만) \n
            - **recruitment**: 원하는 동아리 원 설명 (string, 500자 미만) \n
            """)

    @PatchMapping("/{clubId}/introduction")
    public ApiResponse<?> patchClubIntroduction(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 소개 - 소개글", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 소개 삭제", description = """
            ## 동아리 소개를 삭제합니다.
            - **clubId**: 삭제할 동아리의 ID
            """)
    @DeleteMapping("/{clubId}/introduction")
    public ApiResponse<?> deleteClubIntroduction(@PathVariable Long clubId) {
        return null;
    }



    /*----------------------------- 동아리 모집 안내 ------------------------------*/
    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 조회", description = """
            ## 동아리 모집 안내를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/recruitment")
    public ApiResponse<ClubRecruitmentDTO> getClubRecruitment(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 입력", description = """
            ## 동아리 모집 안내를 입력합니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **due**: 동아리 모집 기간 (string, 500자 미만) \n
            - **notice**: 유의사항 (string, 500자 미만) \n
            - **etc**: 기타 동아리 모집 안내 (string, 500자 미만) \n
            """)
    @PostMapping("/{clubId}/recruitment")
    public ApiResponse<?> postClubRecruitment(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 수정", description = """
            ## 동아리 모집 안내를 수정합니다.
            ### Path Variable
            - **clubId**: 입력할 동아리의 ID  \n
            
            ### Request Body
            - **due**: 동아리 모집 기간 (string, 500자 미만) \n
            - **notice**: 유의사항 (string, 500자 미만) \n
            - **etc**: 기타 동아리 모집 안내 (string, 500자 미만) \n
            """)
    @PatchMapping("/{clubId}/recruitment")
    public ApiResponse<?> patchClubRecruitment(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 삭제", description = """
            ## 동아리 모집 안내를 삭제합니다.
            - **clubId**: 삭제할 동아리의 ID
            """)
    @DeleteMapping("/{clubId}/recruitment")
    public ApiResponse<?> deleteClubRecruitment(@PathVariable Long clubId) {
        return null;
    }
}

package kr.hanjari.backend.domain.club.presentation.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.domain.club.application.query.ClubQueryService;
import kr.hanjari.backend.domain.club.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.enums.College;
import kr.hanjari.backend.domain.club.enums.Department;
import kr.hanjari.backend.domain.club.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.enums.SortBy;
import kr.hanjari.backend.domain.club.enums.UnionClubCategory;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubDetailListResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubSearchResponse;
import kr.hanjari.backend.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubSearchController {

    private final ClubQueryService clubQueryService;

    /*------------------------ 동아리 조건 별 조회 ----------------------------*/
    @Tag(name = "동아리 검색 v1", description = "동아리 검색 관련 API")
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
    public ApiResponse<ClubDetailListResponse> getClubsByCondition(
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

    @GetMapping("/central")
    @Tag(name = "동아리 검색 v2", description = "동아리 검색 관련 API")
    @Operation(summary = "[동아리 검색] 중앙 동아리 검색", description = """
            ## 중앙 동아리를 검색합니다.
            - **keyword**: 동아리 이름에서 검색할 키워드 \n
            - **status**: 동아리 모집 상태 \n
            - **sortBy**: 정렬 기준 \n
            - **category**: 중앙 동아리 카테고리 \n
            
            ### 모든 조건은 선택적으로 입력할 수 있습니다. (필수 X)
            아무 값도 입력 하지 않을 경우, 가나다순으로 정렬하여 전체 중앙 동아리를 조회합니다. page의 기본 값은 0, size의 기본 값은 10입니다.
            """)
    public ApiResponse<ClubSearchResponse> getCentralClubsByCondition(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) RecruitmentStatus status,
            @RequestParam(required = false) SortBy sortBy,
            @RequestParam(required = false) CentralClubCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(
                clubQueryService.findCentralClubsByCondition(keyword, status, sortBy, category, page, size));
    }


    @GetMapping("/union")
    @Tag(name = "동아리 검색 v2", description = "동아리 검색 관련 API")
    @Operation(summary = "[동아리 검색] 연합 동아리 검색", description = """
            ## 연합 동아리를 검색합니다.
            - **keyword**: 동아리 이름에서 검색할 키워드 \n
            - **status**: 동아리 모집 상태 \n
            - **sortBy**: 정렬 기준 \n
            - **category**: 연합 동아리 카테고리 \n
            
            ### 모든 조건은 선택적으로 입력할 수 있습니다. (필수 X)
            아무 값도 입력 하지 않을 경우, 가나다순으로 정렬하여 전체 연합 동아리를 조회합니다. page의 기본 값은 0, size의 기본 값은 10입니다.
            """)
    public ApiResponse<ClubSearchResponse> getUnionClubsByCondition(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) RecruitmentStatus status,
            @RequestParam(required = false) SortBy sortBy,
            @RequestParam(required = false) UnionClubCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(
                clubQueryService.findUnionClubsByCondition(keyword, status, sortBy, category, page, size));
    }


    @GetMapping("/college")
    @Tag(name = "동아리 검색 v2", description = "동아리 검색 관련 API")
    @Operation(summary = "[동아리 검색] 단과대 동아리 검색", description = """
            ## 중앙 동아리를 검색합니다.
            - **keyword**: 동아리 이름에서 검색할 키워드 \n
            - **status**: 동아리 모집 상태 \n
            - **sortBy**: 정렬 기준 \n
            - **college**: 단과대 카테고리 \n
            
            ### 모든 조건은 선택적으로 입력할 수 있습니다. (필수 X)
            아무 값도 입력 하지 않을 경우, 가나다순으로 정렬하여 전체 단과대 동아리를 조회합니다. page의 기본 값은 0, size의 기본 값은 10입니다.
            """)
    public ApiResponse<ClubSearchResponse> getCollageClubsByCondition(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) RecruitmentStatus status,
            @RequestParam(required = false) SortBy sortBy,
            @RequestParam(required = false) College college,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(
                clubQueryService.findCollegeClubsByCondition(keyword, status, sortBy, college, page, size));
    }


    @GetMapping("/department")
    @Tag(name = "동아리 검색 v2", description = "동아리 검색 관련 API")
    @Operation(summary = "[동아리 검색] 학과 동아리 검색", description = """
            ## 중앙 동아리를 검색합니다.
            - **keyword**: 동아리 이름에서 검색할 키워드 \n
            - **status**: 동아리 모집 상태 \n
            - **sortBy**: 정렬 기준 \n
            - **college**: 단과대 카테고리 \n
            - **department**: 학과 카테고리 \n
            
            ### 모든 조건은 선택적으로 입력할 수 있습니다. (필수 X)
            아무 값도 입력 하지 않을 경우, 가나다순으로 정렬하여 전체 중앙 동아리를 조회합니다. page의 기본 값은 0, size의 기본 값은 10입니다.
            """)
    public ApiResponse<ClubSearchResponse> getCentralClubsByCondition(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) RecruitmentStatus status,
            @RequestParam(required = false) SortBy sortBy,
            @RequestParam(required = false) College college,
            @RequestParam(required = false) Department department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(
                clubQueryService.findDepartmentClubsByCondition(keyword, status, sortBy, college, department, page,
                        size));
    }

    @GetMapping("/popular")
    @Tag(name = "동아리 검색 v2", description = "동아리 검색 관련 API")
    @Operation(summary = "[동아리 검색] 인기 동아리 검색", description = """
            ## 인기 동아리를 검색합니다.
            """)
    public ApiResponse<ClubSearchResponse> getPopularClubs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(
                clubQueryService.findPopularClubs(page, size));
    }
}

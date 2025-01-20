package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.payload.ApiResponse;
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

    @Tag(name = "동아리 검색", description = "동아리 검색 관련 API")
    /*------------------------ 동아리 조건 별 조회 ----------------------------*/
    @Operation(summary = "[동아리 검색] 동아리 검색", description = """
            ## 입력한 조건에 맞는 동아리를 검색합니다. 
            - **keyword**: 동아리 이름에서 서 검색할 키워드 \n
            - **category**: 동아리 카테고리 \n
            - **status**: 동아리 모집 상태 \n
            - **sortBy**: 정렬 기준 \n
            
            ### 모든 조건은 선택적으로 입력할 수 있습니다. (필수 X)
            """)
    @GetMapping("")
    public ApiResponse<?> getClubsByCondition(
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
            ## <동아리 상세 페이지/동아리 소개> 동아리 상세 정보를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}")
    public ApiResponse<?> getSpecificClub(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
    @Operation(summary = "[동아리 상세] 동아리 상세 정보 입력", description = """
            ## <동아리 상세 페이지/동아리 소개> 동아리 상세 정보를 입력합니다.
            - **clubId**: 입력할 동아리의 ID
            """)
    @PostMapping("/{clubId}")
    public ApiResponse<?> postSpecificClub() {
        return null;
    }

    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
    @Operation(summary = "[동아리 상세] 동아리 상세 정보 수정", description = """
            ## <동아리 상세 페이지/동아리 소개> 동아리 상세 정보를 수정합니다.
            - **clubId**: 수정할 동아리의 ID
            """)
    @PatchMapping("/{clubId}")
    public ApiResponse<?> patchSpecificClub(@PathVariable Long clubId) {
        return null;
    }


    @Tag(name = "동아리 상세", description = "동아리 상세 정보 API")
    @Operation(summary = "[동아리 상세] 동아리 상세 정보 삭제", description = """
            ## <동아리 상세 페이지/동아리 소개> 동아리 상세 정보를 삭제합니다.
            - **clubId**: 삭제할 동아리의 ID
            """)
    @DeleteMapping("/{clubId}")
    public ApiResponse<?> deleteSpecificClub(@PathVariable Long clubId) {
        return null;
    }

    /*----------------------------- 동아리 소개 ------------------------------*/
    @Tag(name = "동아리 소개", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 소개 조회", description = """
            ## <동아리 상세 페이지/동아리 소개> 동아리 소개를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/introduction")
    public ApiResponse<?> getClubIntroduction(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 소개", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 소개 입력", description = """
            ## <동아리 상세 페이지/동아리 소개> 동아리 소개를 입력합니다.
            - **clubId**: 입력할 동아리의 ID
            """)
    @PostMapping("/{clubId}/introduction")
    public ApiResponse<?> postClubIntroduction(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 소개", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 소개 수정", description = """
            ## <동아리 상세 페이지/동아리 소개> 동아리 소개를 수정합니다.
            - **clubId**: 수정할 동아리의 ID
            """)

    @PatchMapping("/{clubId}/introduction")
    public ApiResponse<?> patchClubIntroduction(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 소개", description = "동아리 소개 관련 API")
    @Operation(summary = "[동아리 소개] 동아리 소개 삭제", description = """
            ## <동아리 상세 페이지/동아리 소개> 동아리 소개를 삭제합니다.
            - **clubId**: 삭제할 동아리의 ID
            """)
    @DeleteMapping("/{clubId}/introduction")
    public ApiResponse<?> deleteClubIntroduction(@PathVariable Long clubId) {
        return null;
    }



    /*----------------------------- 동아리 모집 안내 ------------------------------*/
    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 조회", description = """
            ## <동아리 상세 페이지/동아리 모집 안내> 동아리 모집 안내를 조회합니다.
            - **clubId**: 조회할 동아리의 ID
            """)
    @GetMapping("/{clubId}/recruitment")
    public ApiResponse<?> getClubRecruitment(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 입력", description = """
            ## <동아리 상세 페이지/동아리 모집 안내> 동아리 모집 안내를 입력합니다.
            - **clubId**: 입력할 동아리의 ID
            """)
    @PostMapping("/{clubId}/recruitment")
    public ApiResponse<?> postClubRecruitment(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 수정", description = """
            ## <동아리 상세 페이지/동아리 모집 안내> 동아리 모집 안내를 수정합니다.
            - **clubId**: 수정할 동아리의 ID
            """)
    @PatchMapping("/{clubId}/recruitment")
    public ApiResponse<?> patchClubRecruitment(@PathVariable Long clubId) {
        return null;
    }

    @Tag(name = "동아리 모집 안내", description = "동아리 모집 안내 관련 API")
    @Operation(summary = "[동아리 모집 안내] 동아리 모집 안내 삭제", description = """
            ## <동아리 상세 페이지/동아리 모집 안내> 동아리 모집 안내를 삭제합니다.
            - **clubId**: 삭제할 동아리의 ID
            """)
    @DeleteMapping("/{clubId}/recruitment")
    public ApiResponse<?> deleteClubRecruitment(@PathVariable Long clubId) {
        return null;
    }
}

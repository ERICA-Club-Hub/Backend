package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    /*------------------------ 동아리 조건 별 조회 ----------------------------*/
    @Operation(summary = "[동아리 조건 별 조회] 동아리 검색", description = """
            ## 입력한 조건에 맞는 동아리를 검색합니다. 
            - **keyword**: 동아리 이름에서 서 검색할 키워드 \n
            - **category**: 동아리 카테고리 \n
            - **status**: 동아리 모집 상태 \n
            - **sortBy**: 정렬 기준 \n
            
            ### 모든 조건은 선택적으로 입력할 수 있습니다. (필수 X)
            """)
    @GetMapping("/")
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
    @GetMapping("/{clubId}")
    public ApiResponse<?> getSpecificClub(@PathVariable Long clubId) {
        return null;
    }

    // 생성
    @PostMapping("/{clubId}")
    public ApiResponse<?> postSpecificClub(@PathVariable Long clubId) {
        return null;
    }

    // 수정
    @PatchMapping("/{clubId}")
    public ApiResponse<?> patchSpecificClub(@PathVariable Long clubId) {
        return null;
    }


    // 삭제
    @DeleteMapping("/{clubId}")
    public ApiResponse<?> deleteSpecificClub(@PathVariable Long clubId) {
        return null;
    }

    /*----------------------------- 동아리 소개 ------------------------------*/
    @GetMapping("/{clubId}/introduction")
    public ApiResponse<?> getClubIntroduction(@PathVariable Long clubId) {
        return null;
    }

    @PostMapping("/{clubId}/introduction")
    public ApiResponse<?> postClubIntroduction(@PathVariable Long clubId) {
        return null;
    }

    @PatchMapping("/{clubId}/introduction")
    public ApiResponse<?> patchClubIntroduction(@PathVariable Long clubId) {
        return null;
    }

    @DeleteMapping("/{clubId}/introduction")
    public ApiResponse<?> deleteClubIntroduction(@PathVariable Long clubId) {
        return null;
    }



    /*----------------------------- 동아리 모집 안내 ------------------------------*/
    @GetMapping("/{clubId}/recruitment")
    public ApiResponse<?> getClubRecruitment(@PathVariable Long clubId) {
        return null;
    }

    @PostMapping("/{clubId}/recruitment")
    public ApiResponse<?> postClubRecruitment(@PathVariable Long clubId) {
        return null;
    }

    @PatchMapping("/{clubId}/recruitment")
    public ApiResponse<?> patchClubRecruitment(@PathVariable Long clubId) {
        return null;
    }

    @DeleteMapping("/{clubId}/recruitment")
    public ApiResponse<?> deleteClubRecruitment(@PathVariable Long clubId) {
        return null;
    }
}

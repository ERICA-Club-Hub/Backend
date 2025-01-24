package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자", description = "사용자 관련 API")
@CrossOrigin
@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    // 로그인
    @Operation(summary = "[로그인] 로그인", description = """
            ## 로그인을 수행합니다.JWT 기반 Access Token을 발급합니다.
            - **code**: 동아리 별 인증 코드
            """)
    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody String code) {
        return null;
    }

    // 로그아웃
    @Operation(summary = "[로그아웃] 로그아웃", description = """
            ## 로그아웃을 수행합니다. Access Token을 무효화합니다.
            """)
    @PostMapping("/logout")
    public ApiResponse<?> logout() {
        return null;
    }

    // [한자리 관리자용] 인증 코드 재발급
    @Operation(summary = "[한자리 관리자용] 인증 코드 재발급", description = """
            ## 한자리 관리자용으로 인증 코드를 재발급합니다.
            - **clubName**: 동아리 이름
            """)
    @PostMapping("/reissue-code")
    public ApiResponse<?> reissueCode(@RequestParam String clubName) {
        return null;
    }

}

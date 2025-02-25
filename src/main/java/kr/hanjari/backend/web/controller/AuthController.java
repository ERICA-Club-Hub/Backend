package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.security.token.JwtUtil;
import kr.hanjari.backend.service.auth.AuthService;
import kr.hanjari.backend.web.dto.auth.request.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "[인증] 로그인", description = """
            ## 로그인을 수행합니다. JWT 기반 Access Token을 발급합니다.
            - **code**: 인증 코드
            """)
    @PostMapping("/login")
    public ApiResponse<Long> login(@RequestBody LoginRequestDTO request, HttpServletResponse response) {

        String token = authService.login(request);
        response.addHeader("Authorization", "Bearer " + token);
        Long clubId = jwtUtil.extractClubId(token);

        return ApiResponse.onSuccess(clubId);
    }

    @Operation(summary = "[인증] 로그아웃", description = """
            ## 로그아웃을 수행합니다. Access Token을 무효화합니다.
            """)
    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        authService.logout(token);

        return ApiResponse.onSuccess();
    }

}

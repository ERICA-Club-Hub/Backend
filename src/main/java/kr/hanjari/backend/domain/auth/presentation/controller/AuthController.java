package kr.hanjari.backend.domain.auth.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hanjari.backend.domain.auth.application.service.AuthService;
import kr.hanjari.backend.domain.auth.presentation.dto.LoginResultDTO;
import kr.hanjari.backend.domain.auth.presentation.dto.request.LoginRequest;
import kr.hanjari.backend.domain.auth.presentation.dto.response.LoginResponse;
import kr.hanjari.backend.global.payload.ApiResponse;
import kr.hanjari.backend.infrastructure.jwt.JwtUtil;
import kr.hanjari.backend.infrastructure.web.WebUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "[인증] 로그인", description = """
            ## 로그인을 수행합니다. JWT 기반 Access Token을 발급합니다.
            ### Request
            - **code**: 인증 코드
            ### Response
            - **clubId**: 동아리 Id
            - **clubName**: 동아리명
            """)
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {

        LoginResultDTO result = authService.login(request);
        response.addHeader("Authorization", "Bearer " + result.token());

        return ApiResponse.onSuccess(result.loginResponse());
    }

    @Operation(summary = "[인증] 로그아웃", description = """
            ## 로그아웃을 수행합니다. Access Token을 무효화합니다.
            """)
    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request) {

        String token = WebUtil.resolveToken(request);
        authService.logout(token);

        return ApiResponse.onSuccess();
    }

}

package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.security.token.JwtUtil;
import kr.hanjari.backend.service.auth.AuthService;
import kr.hanjari.backend.web.dto.auth.LoginResultDTO;
import kr.hanjari.backend.web.dto.auth.request.LoginRequestDTO;
import kr.hanjari.backend.web.dto.auth.response.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "인증 관련 API")
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
    public ApiResponse<LoginResponseDTO> login(@RequestBody LoginRequestDTO request, HttpServletResponse response) {

        LoginResultDTO loginResultDTO = authService.login(request);
        response.addHeader("Authorization", "Bearer " + loginResultDTO.token());

        LoginResponseDTO result = LoginResponseDTO.of(loginResultDTO.clubId(), loginResultDTO.clubName());
        return ApiResponse.onSuccess(result);
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

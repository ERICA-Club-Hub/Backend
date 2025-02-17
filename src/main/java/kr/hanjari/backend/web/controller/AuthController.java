package kr.hanjari.backend.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.security.token.JwtUtil;
import kr.hanjari.backend.service.auth.AuthService;
import kr.hanjari.backend.service.club.ClubUtil;
import kr.hanjari.backend.web.dto.auth.request.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final ClubUtil clubUtil;

    @PostMapping("/login")
    public ApiResponse<Void> login(@RequestBody LoginRequestDTO request, HttpServletResponse response) {

        String token = authService.login(request);
        response.addHeader("Authorization", "Bearer " + token);

        return ApiResponse.onSuccess();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        authService.logout(token);

        return ApiResponse.onSuccess();
    }

    @PostMapping("/reissue")
    public ApiResponse<String> reissueClubCode(@RequestParam Long clubId) {

        String result = clubUtil.reissueClubCode(clubId);
        return ApiResponse.onSuccess(result);
    }
}

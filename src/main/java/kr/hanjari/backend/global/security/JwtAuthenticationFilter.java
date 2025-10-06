package kr.hanjari.backend.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hanjari.backend.global.payload.ApiResponse;
import kr.hanjari.backend.global.payload.dto.ErrorReasonDTO;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.infrastructure.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);
        if (token != null) {
            try {
                jwtUtil.validateJwtToken(token);
            } catch (GeneralException e) {
                handleException(response, e);
                return;
            }

            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);;
        }
        filterChain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, GeneralException e) throws IOException {
        ErrorReasonDTO errorReason = e.getErrorReasonHttpStatus();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorReason.getHttpStatus().value());

        ApiResponse<Object> body = ApiResponse.onFailure(errorReason.getCode(), errorReason.getMessage(), null);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(body));

    }



}

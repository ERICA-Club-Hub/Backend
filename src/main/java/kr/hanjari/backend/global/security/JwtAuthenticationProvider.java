package kr.hanjari.backend.global.security;

import kr.hanjari.backend.infrastructure.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public Authentication getAuthentication(String token) {

        Long clubId = jwtUtil.extractClubId(token);
        String role = jwtUtil.extractRole(token);

        UserDetails principal = userDetailsService.loadUserByClubIdAndRole(clubId, role);

        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

    }

}

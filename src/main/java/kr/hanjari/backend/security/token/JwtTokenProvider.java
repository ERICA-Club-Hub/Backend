package kr.hanjari.backend.security.token;

import kr.hanjari.backend.security.detail.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public Authentication getAuthentication(String token) {

        Long clubId = jwtUtil.extractClubId(token);
        String role = jwtUtil.extractRole(token);

        UserDetails principal = userDetailsService.loadUserByClubIdAndRole(clubId, role);

        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

    }

}

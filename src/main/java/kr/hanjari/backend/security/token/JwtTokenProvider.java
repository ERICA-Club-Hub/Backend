package kr.hanjari.backend.security.token;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import kr.hanjari.backend.domain.enums.Role;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.security.detail.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration-time}")
    private Integer EXPIRATION_TIME; // 24시간
    @Value("${jwt.secret.admin}")
    private String SECRET_ADMIN;
    @Value("${jwt.secret.service-admin}")
    private String SECRET_SERVICE_ADMIN;
    @Value("${jwt.blacklist-key}")
    private String BLACKLIST_KEY;

    private final StringRedisTemplate redisTemplate;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

//    public String createAdminToken() {
//        return createToken(0L, Role.ADMIN.getRole());
//    }
//
//    public String createServiceAdminToken() {
//        return createToken(0L, Role.SERVICE_ADMIN.getRole());
//    }
//
//    public String createClubAdminToken(Long clubId) {
//        return createToken(clubId, Role.CLUB_ADMIN.getRole());
//    }
//
//    public String createToken(Long clubId, String role) {
//        Claims claims = Jwts.claims();
//        claims.put("clubId", clubId);
//        claims.put("role", role);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 24시간 유효
//                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//                .compact();
//    }
//
//    public void addTokenToBlacklist(String token) {
//        redisTemplate.opsForSet().add(BLACKLIST_KEY, token);
//        redisTemplate.expire(BLACKLIST_KEY, 1, TimeUnit.DAYS);
//    }
//
//    public void isAccessible(String clubName) {
//        validateTokenAndCheckAccess(clubName);
//    }
//
//    public void isServiceAdminAccessible() {
//        validateTokenAndCheckAccess(SECRET_SERVICE_ADMIN);
//    }
//
//    public void isAdminAccessible() {
//        validateTokenAndCheckAccess(SECRET_ADMIN);
//    }
//
//    public boolean isTokenInBlacklist(String token) {
//        validateJwtToken(token);
//        return redisTemplate.opsForSet().isMember(BLACKLIST_KEY, token);
//    }
//
//    private Claims getClaimsFromToken(String token) {
//        try {
//            return Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (JwtException e) {
//            throw new GeneralException(ErrorStatus._INVALID_TOKEN);
//        }
//    }
//
//    public Long extractClubId(String token) {
//        return getClaimsFromToken(token).get("clubId", Long.class);
//    }
//
//    public Role extractRole(String token) {
//        return getClaimsFromToken(token).get("role", Role.class);
//    }
//
//    // 공통 검증 로직 (중복 제거)
//    private void validateTokenAndCheckAccess(String expectedSubject) {
//        String token = getToken("Authorization").substring(7);
//
//        validateJwtToken(token);
//
//        if (isTokenInBlacklist(token)) {
//            throw new GeneralException(ErrorStatus._TOKEN_ALREADY_LOGOUT);
//        }
//
//        String subject = getClubNameFromToken(token);
//        if (!Objects.equals(subject, expectedSubject)) {
//            throw new GeneralException(ErrorStatus._ACCESS_DENIED);
//        }
//    }
//
//    private String getClubNameFromToken(String token) {
//        Claims claims = getClaimsFromToken(token);
//        return claims.getSubject();
//    }
//
//    private String getToken(String tokenName) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                .getRequest();
//
//        String token = request.getHeader(tokenName);
//
//        if (token == null) {
//            throw new GeneralException(ErrorStatus._TOKEN_NOT_EXIST);
//        }
//
//        return token;
//    }
//
//    public void validateJwtToken(String token) {
//        if (isTokenInBlacklist(token)) {
//            throw new GeneralException(ErrorStatus._TOKEN_ALREADY_LOGOUT);
//        }
//        try {
//            // JWT 유효성 검증 로직 (예: 서명, 만료일자, 포맷 등)
//            // 예시: 토큰을 파싱하고 서명 검증
//            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//        } catch (ExpiredJwtException e) {
//            throw new GeneralException(ErrorStatus._TOKEN_EXPIRED);
//        } catch (SignatureException e) {
//            throw new GeneralException(ErrorStatus._INVALID_TOKEN_SIGNATURE);
//        } catch (MalformedJwtException e) {
//            throw new GeneralException(ErrorStatus._INVALID_TOKEN);
//        } catch (JwtException e) {
//            throw new GeneralException(ErrorStatus._INVALID_TOKEN);
//        }
//    }

    public Authentication getAuthentication(String token) {

        Long clubId = jwtUtil.extractClubId(token);
        String role = jwtUtil.extractRole(token);

        UserDetails principal = userDetailsService.loadUserByClubIdAndRole(clubId, role);

        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

    }

}

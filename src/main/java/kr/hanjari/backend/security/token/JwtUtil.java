package kr.hanjari.backend.security.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import kr.hanjari.backend.domain.enums.Role;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration-time}")
    private Integer EXPIRATION_TIME; // 24시간
    @Value("${jwt.blacklist-key}")
    private String BLACKLIST_KEY;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private final StringRedisTemplate redisTemplate;

    public String createAdminToken() {
        return createToken(0L, Role.ADMIN.getRole());
    }

    public String createServiceAdminToken() {
        return createToken(0L, Role.SERVICE_ADMIN.getRole());
    }

    public String createClubAdminToken(Long clubId) {
        return createToken(clubId, Role.CLUB_ADMIN.getRole());
    }

    public void addTokenToBlacklist(String token) {
        redisTemplate.opsForSet().add(BLACKLIST_KEY, token);
        redisTemplate.expire(BLACKLIST_KEY, 1, TimeUnit.DAYS);
    }

    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new GeneralException(ErrorStatus._INVALID_TOKEN);
        }
    }

    public Long extractClubId(String token) {
        return getClaimsFromToken(token).get("clubId", Long.class);
    }

    public String extractRole(String token) {
        return getClaimsFromToken(token).get("role", String.class);
    }

    public String createToken(Long clubId, String role) {
        Claims claims = Jwts.claims();
        claims.put("clubId", clubId);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 24시간 유효
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateJwtToken(String token) {
        if (isTokenInBlacklist(token)) {
            throw new GeneralException(ErrorStatus._TOKEN_ALREADY_LOGOUT);
        }
        try {
            // JWT 유효성 검증 로직 (예: 서명, 만료일자, 포맷 등)
            // 예시: 토큰을 파싱하고 서명 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new GeneralException(ErrorStatus._TOKEN_EXPIRED);
        } catch (SignatureException e) {
            throw new GeneralException(ErrorStatus._INVALID_TOKEN_SIGNATURE);
        } catch (MalformedJwtException e) {
            throw new GeneralException(ErrorStatus._INVALID_TOKEN);
        } catch (JwtException e) {
            throw new GeneralException(ErrorStatus._INVALID_TOKEN);
        }
    }

    public boolean isTokenInBlacklist(String token) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(BLACKLIST_KEY, token));
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) {
            return null;
        }
        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            throw new GeneralException(ErrorStatus._INVALID_TOKEN_FORMAT);
        }
    }

}

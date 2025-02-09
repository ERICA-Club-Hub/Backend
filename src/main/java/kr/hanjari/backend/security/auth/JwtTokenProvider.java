package kr.hanjari.backend.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration-time}")
    private Integer EXPIRATION_TIME; // 24시간
    @Value("${jwt.secret.admin}")
    private String SECRET_ADMIN;
    @Value("${jwt.secret.service-admin}")
    private String SECRET_SERVICE_ADMIN;

    public String createAdminToken() {
        return createToken(SECRET_ADMIN);
    }

    public String createServiceAdminToken() {
        return createToken(SECRET_SERVICE_ADMIN);
    }

    public String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 24시간 유효
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }


    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 기반 접근 권한 검증
    public void isAccessible(String clubName) {
        validateTokenAndCheckAccess(clubName);
    }

    // 서비스 관리자 접근 권한 검증
    public void isServiceAdminAccessible() {
        validateTokenAndCheckAccess(SECRET_SERVICE_ADMIN);
    }

    // 총동연 관리자 접근 권한 검증
    public void isAdminAccessible() {
        validateTokenAndCheckAccess(SECRET_ADMIN);
    }

    // 공통 검증 로직 (중복 제거)
    private void validateTokenAndCheckAccess(String expectedSubject) {
        String token = getToken("Authorization").substring(7);

        if (isTokenExpired(token)) {
            throw new GeneralException(ErrorStatus._TOKEN_ALREADY_LOGOUT);
        }

        String subject = getClubNameFromToken(token);
        if (!Objects.equals(subject, expectedSubject)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
    }



    private String getClubNameFromToken(String token) {
        return validateToken(token).getSubject();
    }

    private String getToken(String tokenName) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String token = request.getHeader(tokenName);

        if (token == null) {
            throw new GeneralException(ErrorStatus._TOKEN_NOT_EXIST);
        }

        return token;
    }

    public boolean isTokenExpired(String token) {
        return validateToken(token).getExpiration().before(new Date());
    }
}

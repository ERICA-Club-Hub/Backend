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

    // 접근 가능한 API인지 확인(토큰이 유효한지, 만료되지 않았는지, 동아리 관리자가 맞는지)
    public void isAccessible(String clubName) {
        String token = getToken("Authorization");
        token = token.substring(7);
        if (isTokenExpired(token)) {
            throw new GeneralException(ErrorStatus._TOKEN_ALREADY_LOGOUT);
        }

        String clubNameByToken = getClubNameFromToken(token);

        if (!Objects.equals(clubName, clubNameByToken)) {
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

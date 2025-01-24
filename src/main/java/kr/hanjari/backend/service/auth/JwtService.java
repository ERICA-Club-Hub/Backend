package kr.hanjari.backend.service.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.security.SignatureException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration-time}")
    private Integer EXPIRATION_TIME; // 24시간

    public String generateToken(String clubName) {
        return Jwts.builder()
                .setSubject(clubName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 24시간 유효
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token); // 유효성 검증
            return true; // 검증 성공
        } catch (ExpiredJwtException e) {
            System.err.println("Token has expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Unsupported JWT: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Malformed JWT: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Token is empty or null: " + e.getMessage());
        }
        return false; // 검증 실패
    }

}

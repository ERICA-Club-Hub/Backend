package kr.hanjari.backend.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration-time}")
    private Integer EXPIRATION_TIME; // 24시간


    public String createToken(String clubName) {
        return Jwts.builder()
                .setSubject(clubName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 24시간 유효
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Claims validateToken(String token) {
        System.out.println("token: " + token);
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getClubNameFromToken(String token) {
        return validateToken(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return validateToken(token).getExpiration().before(new Date());
    }
}

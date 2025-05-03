package com.lorenipson.user_service.service;

import com.lorenipson.user_service.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

public class JWTService {

    private final SecretKey secretKey;
    private final int validSeconds;
    private final JwtParser jwtParser;

    public JWTService(String secretKeyStr, int validSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyStr.getBytes());
        this.validSeconds = validSeconds;
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    /**
     * 計算過期時間，<br>
     * 準備 payload 內容，<br>
     * 簽名後產生 JWT。
     */
    public String createLoginAccessToken(UserDetails user) {

        long expirationMillisecond = Instant.now().plusSeconds(validSeconds).getEpochSecond() * 1000;

        int ages = (user instanceof UserDetailsImpl uImpl) ? uImpl.getAge() : 0;

        Claims claims = Jwts
                .claims()
                .issuedAt(new Date())
                .expiration(new Date(expirationMillisecond))
                .subject(user.getUsername())
                .add("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .add("ages", ages)
                .build();

        return Jwts.builder().claims(claims).signWith(secretKey).compact();

    }

    public Claims parseToken(String jwt) throws JwtException {
        return jwtParser.parseSignedClaims(jwt).getPayload();
    }

    /**
     * 再次驗證 Token 是否合法。<br>
     * 筆記：驗證與 Filter 同時存在且有差異。
     */
    public boolean validateToken(String jwt, UserDetails user) throws JwtException {

        try {
            Claims claims = parseToken(jwt);
            String username = claims.getSubject();
            Date expiration = claims.getExpiration();
            return username.equals(user.getUsername()) && expiration.after(new Date());
        } catch (JwtException e) {
            return false;
        }

    }

}

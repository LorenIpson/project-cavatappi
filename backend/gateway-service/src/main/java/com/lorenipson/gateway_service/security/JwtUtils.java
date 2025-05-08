package com.lorenipson.gateway_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtils {

    private final JwtParser jwtParser;

    public JwtUtils(String secretKeyStr) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyStr.getBytes());
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    public Claims parseToken(String jwt) throws JwtException {
        return jwtParser.parseSignedClaims(jwt).getPayload();
    }

    public boolean validateToken(String jwt) {

        try {
            Claims claims = parseToken(jwt);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (JwtException e) {
            return false;
        }

    }

}

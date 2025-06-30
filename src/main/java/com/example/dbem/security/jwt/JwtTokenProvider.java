package com.example.dbem.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtTokenProvider {
    @Value("${spring.jwt.secret}")
    private String secret;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("smemo")
                .claim("type", "access")
                .setIssuedAt(new Date())
                .setExpiration((new Date(System.currentTimeMillis() + 60 * 60 * 1000)))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("smemo")
                .claim("type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

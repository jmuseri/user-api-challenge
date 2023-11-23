package com.museri.challenge.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtToken {
    private final String issuer;
    private final Integer expires;
    private final String sub;
    private final String secretKey;

    public JwtToken(
            @Value ("${jwt.issuer}") String issuer,
            @Value ("${jwt.expires}") Integer expires,
            @Value ("${jwt.sub}") String sub,
            @Value ("${jwt.secretKey}") String secretKey) {
        this.issuer = issuer;
        this.expires = expires;
        this.sub = sub;
        this.secretKey = secretKey;
    }

    public String generateToken(String email, LocalDateTime lastLogin) {
        Date expiration = Date.from(LocalDateTime.now().plusDays(expires).toInstant(ZoneOffset.UTC));

        return Jwts.builder()
                .setSubject(sub)
                .claim("email", email)
                .claim("lastLogin", lastLogin)
                .setExpiration(expiration)
                .setIssuedAt(new Date())
                .setIssuer(issuer)
                .setId(UUID.randomUUID().toString())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaimsFromToken(String token) throws Exception {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmailFromToken(String token) throws Exception {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("email");
    }

    public boolean isExpiredToken(String token) throws Exception {
        Date expiration = getClaimsFromToken(token).getExpiration();
        return (expiration.before(new Date()));
    }
}
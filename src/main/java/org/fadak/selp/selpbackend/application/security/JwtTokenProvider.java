package org.fadak.selp.selpbackend.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration-minutes}")
    private long accessTokenExpirationMinutes;

    @Value("${jwt.refresh-token-expiration-minutes}")
    private long refreshTokenExpirationMinutes;

    private Key key;

    @PostConstruct
    protected void init(){
        byte[] keyBytes = Base64.getEncoder().encode(secretKey.getBytes());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Long memberId){
        return createToken(memberId, accessTokenExpirationMinutes);
    }

    public String createRefreshToken(Long memberId){
        return createToken(memberId, refreshTokenExpirationMinutes);
    }

    public String createToken(Long memberId, long expirationMinutes){
        Claims claims = Jwts.claims().subject(memberId.toString()).build();
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationMinutes * 60 * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}

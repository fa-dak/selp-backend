package org.fadak.selp.selpbackend.application.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
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
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createAccessToken(Long memberId, Map<String, Object> claims){
        return createToken(memberId, claims, accessTokenExpirationMinutes);
    }

    public String createRefreshToken(Long memberId){
        return createToken(memberId, new HashMap<>(), refreshTokenExpirationMinutes);
    }

    public String createToken(Long memberId, Map<String, Object> claims, long expirationMinutes){
        Claims jwtClaims = Jwts.claims().subject(memberId.toString()).add(claims).build();
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationMinutes * 60 * 1000);

        return Jwts.builder()
                .setClaims(jwtClaims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    /**
     * JWT 토큰 복호화 메서드
     * @param accessToken String : AccessToken
     * @return Authentication : Spring Security 의 인증 객체
     */
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        Long userId = Long.parseLong(claims.getSubject());

        UserPrincipal userPrincipal = new UserPrincipal(userId);

        return new UsernamePasswordAuthenticationToken(userPrincipal, "", userPrincipal.getAuthorities());
    }

    /**
     * 토큰정보 검증 메서드
     * @param token String : 검증할 토큰
     * @throws IllegalArgumentException : 유효하지 않은 토큰일 경우 발생
     */
    public void validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
        } catch (io.jsonwebtoken.security.SignatureException | MalformedJwtException e){
            throw new IllegalArgumentException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e){
            throw new IllegalArgumentException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e){
            throw new IllegalArgumentException("지원되지 않는 형식의 JWT 토큰입니다.");
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims(); // 토큰이 만료돼도 클레임 추출은 가능하게
        }
    }
}

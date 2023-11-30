package com.pacemaker.eta.jwt;

import com.pacemaker.eta.domain.entity.UserRole;
import com.pacemaker.eta.global.exception.BusinessException;
import com.pacemaker.eta.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

@Slf4j
@Getter
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "BEARER";

    private final long ACCESS_TOKEN_EXPIRE_TIME = 30*60*1000L;
    private final long REFRESH_TOKEN_EXPIRE_TIME = 7*24*60*60*1000L;

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    protected String createTokenByKakaoId(Long kakaoId, Set<UserRole> auth, long tokenValid) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(kakaoId));
        claims.put(AUTHORITIES_KEY,
            auth.stream()
                .map(UserRole::getAbbreviation
                )
                .collect(Collectors.joining(",")));
        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims) // 토큰 발행 유저 정보
            .setIssuedAt(now) // 토큰 발행 시간
            .setExpiration(new Date(now.getTime() + tokenValid)) // 토큰 만료시간
            .signWith(key, SignatureAlgorithm.HS512) // 키와 알고리즘 설정
            .compact();
    }

    public String createAccessTokenByKakaoId(Long kakaoId, Set<UserRole> auth) {
        return this.createTokenByKakaoId(kakaoId, auth, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String createRefreshToken(Long kakaoId, Set<UserRole> auth) {
        return this.createTokenByKakaoId(kakaoId, auth, REFRESH_TOKEN_EXPIRE_TIME);
    }

    public Authentication getAuthenticationByKakaoId(String accessToken) throws BusinessException {

        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null || !StringUtils.hasText(claims.get(AUTHORITIES_KEY).toString())) {
            throw BusinessException.from(ErrorCode.NOT_FOUND_AUTHORITY);
        }

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        authorities.stream().forEach(o->{
            log.debug("getAuthentication -> authorities = {}",o.getAuthority());
        });

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new CustomKakaoIdAuthToken(principal, "", authorities);
    }

    public int validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return 1;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            return 2;
        } catch (Exception e) {
            log.info("잘못된 토큰입니다.");
            return 3;
        }
    }
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) { // 만료된 토큰이 더라도 일단 파싱을 함
            return e.getClaims();
        }
    }
    public TokenDto createTokenDTO(String accessToken, String refreshToken) {
        return TokenDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .grantType(BEARER_TYPE)
            .build();
    }

    public Long getMemberKakaoIdByToken(String token) {
        return Long.getLong(this.parseClaims(token).getSubject());
    }
}





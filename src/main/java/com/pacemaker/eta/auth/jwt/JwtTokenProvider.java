package com.pacemaker.eta.auth.jwt;

import com.pacemaker.eta.global.config.security.ExpireTime;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "${security.jwt.token.secret-key}";
    private static final String EXPIRE_LENGTH = "${security.jwt.token.expire-length}";

    private final SecretKey key;
    private final long validityInMilliseconds;

    public JwtTokenProvider(@org.springframework.beans.factory.annotation.Value(SECRET_KEY) final String secretKey,
        @Value(EXPIRE_LENGTH) final long validityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(TokenPayload payload) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
            .addClaims(payload.toMap())
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public TokenPayload getPayload(String token) {
        Claims claims = parseClaimsJws(token).getBody();
        Long id = claims.get("id", Long.class);
        return new TokenPayload(id);
    }

    private Jws<Claims> parseClaimsJws(final String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
    }

    public boolean isValidAccessToken(String accessToken) {
        try {
            return !parseClaimsJws(accessToken)
                .getBody()
                .getExpiration()
                .before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

package com.pacemaker.eta.auth.jwt;

import com.pacemaker.eta.global.exception.BusinessException;
import com.pacemaker.eta.global.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenProvider {

    private static final String EXPIRE_LENGTH = "${security.refresh.token.expire-day}";

    private final long validityInDays;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenProvider(@Value(EXPIRE_LENGTH) long validityInDays, RefreshTokenRepository refreshTokenRepository) {
        this.validityInDays = validityInDays;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createToken(TokenPayload payload) {
        String value = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        RefreshToken refreshToken = new RefreshToken(value, now.plusDays(validityInDays), payload.id());
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken reIssueToken(String refreshTokenValue) {
        RefreshToken refreshToken = getRefreshToken(refreshTokenValue);
        RefreshToken newRefreshToken = createToken(new TokenPayload(refreshToken.getMemberId()));
        refreshTokenRepository.delete(refreshToken);
        return newRefreshToken;
    }

    private RefreshToken getRefreshToken(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenRepository.findByValue(refreshTokenValue)
            .orElseThrow(() -> BusinessException.from(ErrorCode.NOT_FOUND_REFRESH_TOKEN_IN_REPOSITORY));
        if (!refreshToken.isValid(LocalDateTime.now())) {
            throw BusinessException.from(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
        return refreshToken;
    }

    public void deleteToken(String refreshTokenValue) {
        refreshTokenRepository.findByValue(refreshTokenValue)
            .ifPresent(refreshTokenRepository::delete);
    }
}


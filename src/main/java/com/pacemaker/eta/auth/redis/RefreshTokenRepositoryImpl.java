package com.pacemaker.eta.auth.redis;

import com.pacemaker.eta.auth.jwt.RefreshToken;
import com.pacemaker.eta.auth.jwt.RefreshTokenRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;


    @Override
    public void save(RefreshToken refreshToken) {
        refreshTokenRedisRepository.save(new RedisRefreshToken(refreshToken));
    }

    @Override
    public Optional<RefreshToken> findByValue(String refreshTokenValue) {
        return refreshTokenRedisRepository.findById(refreshTokenValue)
            .map(RedisRefreshToken::toRefreshToken);
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        refreshTokenRedisRepository.delete(new RedisRefreshToken(refreshToken));
    }
}


package com.pacemaker.eta.auth.redis;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RedisRefreshToken, String> {
}

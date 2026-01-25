package com.spotAlert.backend.Health;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisHealthIndicator implements HealthIndicator {

    private final StringRedisTemplate redisTemplate;

    @Override
    public Health health() {
        try {
            String pong = redisTemplate.getConnectionFactory()
                    .getConnection()
                    .ping();

            if ("PONG".equalsIgnoreCase(pong)) {
                log.info("Redis health check successful");
                return Health.up()
                        .withDetail("redis", "Available")
                        .build();
            }

            log.warn("Redis ping failed");
            return Health.down()
                    .withDetail("redis", "Ping failed")
                    .build();

        } catch (Exception ex) {
            log.error("Redis health check failed", ex);
            return Health.down(ex).build();
        }
    }
}

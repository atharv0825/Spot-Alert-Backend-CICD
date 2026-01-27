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
            var connectionFactory = redisTemplate.getConnectionFactory();

            if (connectionFactory == null) {
                return Health.down()
                        .withDetail("redis", "No connection factory")
                        .build();
            }

            connectionFactory.getConnection().ping();

            return Health.up()
                    .withDetail("redis", "Available")
                    .build();

        } catch (Exception ex) {
            log.warn("Redis health check failed fast", ex);
            return Health.down()
                    .withDetail("redis", "Unavailable")
                    .build();
        }
    }
}

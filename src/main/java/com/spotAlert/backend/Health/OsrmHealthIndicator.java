package com.spotAlert.backend.Health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class OsrmHealthIndicator implements HealthIndicator {

    @Value("${osrm.base-url}")
    private String osrmBaseUrl;

    @Value("${osrm.profile}")
    private String profile;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Health health() {
        try {
            // Dummy coordinates (same point twice)
            String url = String.format(
                    "%s/route/v1/%s/74.2430,16.7144;74.2430,16.7144",
                    osrmBaseUrl,
                    profile
            );

            restTemplate.getForObject(url, String.class);

            log.info("OSRM health check successful");
            return Health.up()
                    .withDetail("osrm", "Available")
                    .build();

        } catch (Exception ex) {
            log.error("OSRM health check failed", ex);
            return Health.down()
                    .withDetail("osrm", "Unavailable")
                    .withException(ex)
                    .build();
        }
    }
}

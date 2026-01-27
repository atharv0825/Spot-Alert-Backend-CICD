package com.spotAlert.backend.Health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class OsrmHealthIndicator implements HealthIndicator {

    @Value("${osrm.base-url}")
    private String osrmBaseUrl;

    @Value("${osrm.profile}")
    private String profile;

    private final RestTemplate restTemplate;

    public OsrmHealthIndicator() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(1000);
        factory.setReadTimeout(2000);
        this.restTemplate = new RestTemplate(factory);
    }

    @Override
    public Health health() {
        try {
            String url = String.format(
                    "%s/route/v1/%s/74.2430,16.7144;74.2430,16.7144",
                    osrmBaseUrl,
                    profile
            );

            restTemplate.getForObject(url, String.class);

            return Health.up()
                    .withDetail("osrm", "Available")
                    .build();

        } catch (Exception ex) {
            log.warn("OSRM health check failed fast", ex);
            return Health.down()
                    .withDetail("osrm", "Unavailable")
                    .build();
        }
    }
}

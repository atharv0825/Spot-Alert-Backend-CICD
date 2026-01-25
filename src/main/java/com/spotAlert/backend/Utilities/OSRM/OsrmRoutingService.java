package com.spotAlert.backend.Utilities.OSRM;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotAlert.backend.DTO.RouteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class OsrmRoutingService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${osrm.base-url}")
    private String osrmBaseUrl;

    @Value("${osrm.profile}")
    private String profile;

    @Cacheable(
            value = "osrm-routes",
            key = "'route:' + #sourceLat + ',' + #sourceLng + ':' + #destLat + ',' + #destLng"
    )
    public RouteResponse getRoute(
            double sourceLat,
            double sourceLng,
            double destLat,
            double destLng
    ) {
        log.info("Redis cache miss");
        String url = String.format(
                "%s/route/v1/%s/%f,%f;%f,%f?overview=full&geometries=polyline&steps=false",
                osrmBaseUrl,
                profile,
                sourceLng, sourceLat,
                destLng, destLat
        );

        log.info("OSRM request URL: {}", url);

        try {
            String responseBody = restTemplate.getForObject(url, String.class);

            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode routes = root.get("routes");

            if (routes == null || routes.isEmpty()) {
                throw new RuntimeException("No routes found in OSRM response");
            }

            JsonNode route = routes.get(0);

            return RouteResponse.builder()
                    .distanceMeters(route.get("distance").asDouble())
                    .durationSeconds(route.get("duration").asDouble())
                    .polyline(route.get("geometry").asText())
                    .build();

        } catch (Exception e) {
            log.error("OSRM error", e);
            throw new RuntimeException("Failed to fetch route from OSRM");
        }
    }
}

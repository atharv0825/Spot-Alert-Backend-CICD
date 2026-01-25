package com.spotAlert.backend.DTO;

import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HazardsDTO {

    private String id;
    private String type;
    private double latitude;
    private double longitude;
    private String title;
    private String description;
    private int severity;
    private String source;
    private Boolean verified;
    private String roadName;
    private String city;
    private GeoJsonPoint location;
}

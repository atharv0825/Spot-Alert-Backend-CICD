package com.spotAlert.backend.DTO;

import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HazardsResponseDTO {
    private String id;
    private String type;
    private double latitude;
    private double longitude;
    private String title;
    private String description;
    private int severity;
}

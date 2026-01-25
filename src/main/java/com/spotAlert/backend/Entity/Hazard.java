package com.spotAlert.backend.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("Hazardous")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Hazard {
    @Id
    private String id;
    private String type; // accident-prone , speed-bump , traffic-signal , school-zone , roundabout , sharp-turn , railway-crossing
    private double latitude;
    private double longitude;
    private String title; // Accident Prone Zone , Speed Camera (this required for show on the map)
    private String description; // Alert for the particular location
    private int severity; // for the check the intensity of the black spot
    private String source; // from where the spot came from OSM , User-report , admin
    private LocalDateTime spotAddedAt;
    private Boolean verified;
    private String roadName;
    public String city;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
}

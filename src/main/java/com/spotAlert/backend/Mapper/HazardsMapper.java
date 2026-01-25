package com.spotAlert.backend.Mapper;

import com.spotAlert.backend.DTO.HazardsDTO;
import com.spotAlert.backend.DTO.HazardsResponseDTO;
import com.spotAlert.backend.Entity.Hazard;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDateTime;

public class HazardsMapper {

    public static Hazard toEntity(HazardsDTO dto) {

        return Hazard.builder()
                .id(dto.getId())
                .type(dto.getType())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .title(dto.getTitle())
                .source(dto.getSource())
                .description(dto.getDescription())
                .severity(dto.getSeverity())
                .roadName(dto.getRoadName())
                .city(dto.getCity())
                .verified(dto.getVerified())
                .spotAddedAt(LocalDateTime.now())
                .location((new GeoJsonPoint(dto.getLongitude(), dto.getLatitude())))
                .build();
    }

    public static HazardsDTO toDTO(Hazard h) {
        return HazardsDTO.builder()
                .id(h.getId())
                .type(h.getType())
                .latitude(h.getLatitude())
                .longitude(h.getLongitude())
                .title(h.getTitle())
                .description(h.getDescription())
                .severity(h.getSeverity())
                .source(h.getSource())
                .verified(h.getVerified())
                .roadName(h.getRoadName())
                .location(h.getLocation())
                .city(h.getCity())
                .build();
    }

    public static HazardsResponseDTO toResponseDTO(Hazard h) {
        return HazardsResponseDTO.builder()
                .id(h.getId())
                .type(h.getType())
                .latitude(h.getLatitude())
                .longitude(h.getLongitude())
                .title(h.getTitle())
                .description(h.getDescription())
                .severity(h.getSeverity())
                .build();
    }
}

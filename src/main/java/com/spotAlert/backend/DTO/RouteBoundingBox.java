package com.spotAlert.backend.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RouteBoundingBox {
    private double minLat;
    private double maxLat;
    private double minLng;
    private double maxLng;
}

package com.spotAlert.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RoutePoint {
    private final double lat;
    private final double lng;
    private int index;
}

package com.spotAlert.backend.Utilities.OSRM;

import com.spotAlert.backend.DTO.RouteBoundingBox;
import com.spotAlert.backend.DTO.RoutePoint;

import java.util.List;

public class RouteBoundingBoxUtil {

    private RouteBoundingBoxUtil() {
        // utility class
    }

    public static RouteBoundingBox compute(List<RoutePoint> route) {

        double minLat = Double.MAX_VALUE;
        double maxLat = Double.MIN_VALUE;
        double minLng = Double.MAX_VALUE;
        double maxLng = Double.MIN_VALUE;

        for (RoutePoint p : route) {
            minLat = Math.min(minLat, p.getLat());
            maxLat = Math.max(maxLat, p.getLat());
            minLng = Math.min(minLng, p.getLng());
            maxLng = Math.max(maxLng, p.getLng());
        }

        return new RouteBoundingBox(minLat, maxLat, minLng, maxLng);
    }
}

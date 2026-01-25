package com.spotAlert.backend.Utilities.OSRM;

import com.spotAlert.backend.DTO.RouteBoundingBox;

public class BoundingBoxExpander {

    private BoundingBoxExpander() {
    }

    public static RouteBoundingBox expand(
            RouteBoundingBox box,
            double meters
    ) {
        // converting distance in meter to degree 1 degree ≈ 111 km
        double delta = meters / 111_000.0;

        return new RouteBoundingBox(
                box.getMinLat() - delta,
                box.getMaxLat() + delta,
                box.getMinLng() - delta,
                box.getMaxLng() + delta
        );
    }
}

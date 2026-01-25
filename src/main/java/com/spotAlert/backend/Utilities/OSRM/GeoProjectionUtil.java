package com.spotAlert.backend.Utilities.OSRM;

public class GeoProjectionUtil {

    private static final double EARTH_RADIUS = 6371000; // meters

    public static double[] toXY(
            double lat,
            double lng,
            double refLat
    ) {
        double x = Math.toRadians(lng) * EARTH_RADIUS * Math.cos(Math.toRadians(refLat));
        double y = Math.toRadians(lat) * EARTH_RADIUS;
        return new double[]{x, y};
    }
}

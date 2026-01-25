package com.spotAlert.backend.Utilities.OSRM;

import com.spotAlert.backend.DTO.RoutePoint;

public class PointToSegmentDistanceUtil {

    public static double distanceMeters(
            RoutePoint a,
            RoutePoint b,
            double hazardLat,
            double hazardLng
    ) {
        double refLat = a.getLat();

        double[] A = GeoProjectionUtil.toXY(
                a.getLat(), a.getLng(), refLat);
        double[] B = GeoProjectionUtil.toXY(
                b.getLat(), b.getLng(), refLat);
        double[] H = GeoProjectionUtil.toXY(
                hazardLat, hazardLng, refLat);

        double ABx = B[0] - A[0];
        double ABy = B[1] - A[1];
        double AHx = H[0] - A[0];
        double AHy = H[1] - A[1];

        double ab2 = ABx * ABx + ABy * ABy;
        double dot = AHx * ABx + AHy * ABy;

        double t = dot / ab2;

        // Clamp projection to segment
        t = Math.max(0, Math.min(1, t));

        double closestX = A[0] + t * ABx;
        double closestY = A[1] + t * ABy;

        double dx = H[0] - closestX;
        double dy = H[1] - closestY;

        return Math.sqrt(dx * dx + dy * dy);
    }
}

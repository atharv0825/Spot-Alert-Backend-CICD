package com.spotAlert.backend.Utilities.OSRM;

import com.spotAlert.backend.DTO.RoutePoint;

import java.util.List;

public class UserOffRoute {
    public UserOffRoute() {
    }

    public static boolean isUserOffRoute(
            double userLat,
            double userLng,
            List<RoutePoint> route,
            double thresholdMeters
    ) {
        for (int i = 0; i < route.size() - 1; i++) {

            RoutePoint a = route.get(i);
            RoutePoint b = route.get(i + 1);

            double distance = PointToSegmentDistanceUtil.distanceMeters(
                    a, b,
                    userLat,
                    userLng
            );

            if (distance <= thresholdMeters) {
                return false; // still on route
            }
        }
        return true; // off route
    }
}

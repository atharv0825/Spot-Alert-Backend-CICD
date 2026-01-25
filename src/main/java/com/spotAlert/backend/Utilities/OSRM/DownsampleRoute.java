package com.spotAlert.backend.Utilities.OSRM;

import com.spotAlert.backend.DTO.RoutePoint;
import com.spotAlert.backend.Utilities.DistanceUtil;

import java.util.ArrayList;
import java.util.List;

public class DownsampleRoute {

    public static List<RoutePoint> downsample(
            List<RoutePoint> original,
            double minDistanceMeters
    ) {

        List<RoutePoint> filtered = new ArrayList<>();

        RoutePoint lastKept = null;

        for (RoutePoint current : original) {

            if (lastKept == null) {
                filtered.add(current);
                lastKept = current;
                continue;
            }

            double distance = DistanceUtil.calculateDistance(
                    lastKept.getLat(),
                    lastKept.getLng(),
                    current.getLat(),
                    current.getLng()
            );  // here calculating distance between last point and current point it
            // should be greater than 15 meters


            if (distance >= minDistanceMeters) {
                filtered.add(current);
                lastKept = current;
            }
        }

        return filtered;
    }
}

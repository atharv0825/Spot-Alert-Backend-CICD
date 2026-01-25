package com.spotAlert.backend.Utilities.OSRM;

import com.spotAlert.backend.DTO.RoutePoint;

import java.util.ArrayList;
import java.util.List;

public class PolylineDecoder {

    public static List<RoutePoint> decode(String encoded) {

        List<RoutePoint> points = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        int routeIndex = 0;


        while (index < len) {

            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlat = ((result & 1) != 0) ? ~(result >> 1) : (result >> 1);
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlng = ((result & 1) != 0) ? ~(result >> 1) : (result >> 1);
            lng += dlng;

            points.add(new RoutePoint(
                    lat / 1E5,
                    lng / 1E5,
                    routeIndex++
            ));

        }

        return points;
    }
}

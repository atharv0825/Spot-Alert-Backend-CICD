package com.spotAlert.backend.Service;

import com.spotAlert.backend.DTO.*;
import com.spotAlert.backend.Entity.Hazard;
import com.spotAlert.backend.Repository.HazardRepository;
import com.spotAlert.backend.Utilities.OSRM.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

    private final OsrmRoutingService osrmRoutingService;
    private final HazardRepository hazardRepository;

    public RouteResponse getBasicRoute(RouteRequest request) {

        RouteResponse response = osrmRoutingService.getRoute(
                request.getSourceLat(),
                request.getSourceLng(),
                request.getDestLat(),
                request.getDestLng()
        );
        List<RoutePoint>optimizedPoints = getOptimizedRoutePoints(response); // downsampled Point

        int userIndex = HazardousOnRoute.findUserRouteIndex(request.getSourceLat(),request.getSourceLng(),optimizedPoints);
        log.info("User current route index: {}", userIndex);
        // here we got the user current location index

        // now creating bounding box
        RouteBoundingBox expandedBox = getExpandedBoundingBox(optimizedPoints);

        // get hazards which are on the route
        List<HazardRouteMatch> hazardsOnRoad = getHazardsOnRoad(expandedBox , optimizedPoints);

        //by using users current location track the upcoming points ignore passed points
        List<HazardRouteMatch>upcomingHazardsOnRoad = getUpcomingHazardsOnRoad(hazardsOnRoad , userIndex);
        response.setAllHazardsOnRoute(upcomingHazardsOnRoad);
        response.setTotalHazardsCount(upcomingHazardsOnRoad.size());
        return response;
    }


    private List<RoutePoint>getOptimizedRoutePoints(RouteResponse response){
        List<RoutePoint>allRoutePoints = PolylineDecoder.decode(response.getPolyline());
        log.info("Route points count: {}", allRoutePoints.size());
        List<RoutePoint>optimizedPoints = DownsampleRoute.downsample(allRoutePoints,15);
        log.info("Downsampled points: {}", optimizedPoints.size());

        return optimizedPoints;
    }

    private RouteBoundingBox getExpandedBoundingBox(List<RoutePoint>optimizedPoints){
        RouteBoundingBox box = RouteBoundingBoxUtil.compute(optimizedPoints);

        RouteBoundingBox expandedBox = BoundingBoxExpander.expand(box,100);
        log.info("Expanded box: lat {} → {}, lng {} → {}",
                expandedBox.getMinLat(), expandedBox.getMaxLat(),
                expandedBox.getMinLng(), expandedBox.getMaxLng());

        return expandedBox;
    }

    private List<HazardRouteMatch>getHazardsOnRoad(RouteBoundingBox expandedBox ,List<RoutePoint>optimizedPoints ){
        List<Hazard>candidates = hazardRepository.findWithinBoundingBox(expandedBox.getMinLng(), expandedBox.getMinLat(),expandedBox.getMaxLng(), expandedBox.getMaxLat());
        log.info("Hazards inside expanded bounding box: {}", candidates.size());
        List<HazardRouteMatch> matched = candidates.stream()
                .map(h -> HazardousOnRoute.matchHazardToRoute(h, optimizedPoints, 40))
                .filter(HazardRouteMatch::isOnRoute)
                .toList();
        log.info("Hazards ON route after perpendicular filter: {}", matched.size());
        return matched;
    }

    private List<HazardRouteMatch>getUpcomingHazardsOnRoad(List<HazardRouteMatch> matched , int userIndex){
        return matched.stream()
                .filter(h -> h.getRouteIndex() > userIndex)
                .toList();
    }
    

}

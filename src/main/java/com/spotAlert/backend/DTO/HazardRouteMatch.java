package com.spotAlert.backend.DTO;

import com.spotAlert.backend.Entity.Hazard;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HazardRouteMatch {
    private boolean onRoute;
    private int routeIndex;
    private double distance;
    private HazardsResponseDTO hazards;
}

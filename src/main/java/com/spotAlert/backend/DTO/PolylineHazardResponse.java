package com.spotAlert.backend.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PolylineHazardResponse {

    private int totalHazards;
    private List<HazardRouteMatch> hazards;
}

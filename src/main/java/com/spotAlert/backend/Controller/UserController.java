package com.spotAlert.backend.Controller;

import com.spotAlert.backend.DTO.HazardsDTO;
import com.spotAlert.backend.DTO.HazardsResponseDTO;
import com.spotAlert.backend.DTO.RouteRequest;
import com.spotAlert.backend.DTO.RouteResponse;
import com.spotAlert.backend.Service.HazardService;
import com.spotAlert.backend.Service.RouteService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private HazardService hazardService;
    @Autowired
    private  RouteService routeService;

    public UserController(HazardService hazardService) {
        this.hazardService = hazardService;
    }

    @PostMapping("/add")
    public ResponseEntity<HazardsDTO> addByUser(@RequestBody HazardsDTO dto) {
        dto.setSource("USER");
        dto.setVerified(false);
        HazardsDTO result = hazardService.addHazards(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/route/optimal")
    public ResponseEntity<RouteResponse> getBasicRoute(@Valid @RequestBody RouteRequest request) {
        RouteResponse response = routeService.getBasicRoute(request);
        return ResponseEntity.ok(response);
    }


}
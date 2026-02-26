package com.spotAlert.backend.Controller;

import com.spotAlert.backend.DTO.*;
import com.spotAlert.backend.Service.HazardService;
import com.spotAlert.backend.Service.RouteService;
import com.spotAlert.backend.Service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final HazardService hazardService;
    private final RouteService routeService;
    private final UserService userService;

    public UserController(HazardService hazardService, RouteService routeService, UserService userService) {
        this.hazardService = hazardService;
        this.routeService = routeService;
        this.userService = userService;
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

    @PostMapping("/route/hazards-by-polyline")
    public ResponseEntity<PolylineHazardResponse> getHazardsByPolyline(
            @RequestBody PolylineHazardRequest request) {

        PolylineHazardResponse response =
                routeService.getHazardsFromPolyline(request.getPolyline());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/hazards")
    public ResponseEntity<List<HazardsResponseDTO>> getHazardsByCity(
            @RequestParam String city) {
        return ResponseEntity.ok(hazardService.getHazardsByCity(city));
    }

    @PostMapping("/get-details")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@RequestBody UserDetailsByEmailRequest request) {
        return ResponseEntity.ok(userService.getUserDetails(request));
    }

    @PostMapping("/update-details")
    public ResponseEntity<UserUpdateList> updateUserDetails(@RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.setUserUpdateList(request));
    }

}
package com.spotAlert.backend.Controller;

import com.spotAlert.backend.DTO.HazardsDTO;
import com.spotAlert.backend.DTO.HazardsResponseDTO;
import com.spotAlert.backend.Service.HazardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final HazardService hazardService;

    public AdminController(HazardService hazardService) {
        this.hazardService = hazardService;
    }

    @PostMapping("/add")
    public ResponseEntity<HazardsDTO> addByAdmin(@RequestBody HazardsDTO dto) {
        dto.setSource("ADMIN");
        dto.setVerified(true);
        return ResponseEntity.ok(hazardService.addHazards(dto));
    }

    @GetMapping("/hazards")
    public ResponseEntity<List<HazardsResponseDTO>> getHazardsByCity(
            @RequestParam String city) {
        return ResponseEntity.ok(hazardService.getHazardsByCity(city));
    }

    @GetMapping("/unverified")
    public ResponseEntity<List<HazardsResponseDTO>> getUnverifiedHazardsByCity(
            @RequestParam String city) {
        return ResponseEntity.ok(hazardService.getUnverifiedHazardsByCity(city));
    }

    @PutMapping("/verify")
    public ResponseEntity<String> verifySingle(@RequestParam String id) {
        hazardService.verifyHazards(id);
        return ResponseEntity.ok("Hazard verified successfully");
    }

    @PutMapping("/multi-verify")
    public ResponseEntity<String> verifyMultiple(@RequestBody List<String> ids) {
        hazardService.verifyMultipleHazards(ids);
        return ResponseEntity.ok("Hazards verified successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHazard(@PathVariable String id) {
        hazardService.deleteHazard(id);
        return ResponseEntity.ok("Hazard deleted successfully");
    }

    @DeleteMapping("/delete/bulk")
    public ResponseEntity<String> deleteHazardsBulk(@RequestBody List<String> ids) {
        hazardService.deleteHazardsBulk(ids);
        return ResponseEntity.ok("Hazards deleted successfully");
    }
}

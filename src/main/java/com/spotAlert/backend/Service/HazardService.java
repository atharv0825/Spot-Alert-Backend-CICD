package com.spotAlert.backend.Service;

import com.spotAlert.backend.DTO.HazardsDTO;
import com.spotAlert.backend.DTO.HazardsResponseDTO;
import com.spotAlert.backend.Entity.Hazard;
import com.spotAlert.backend.Mapper.HazardsMapper;
import com.spotAlert.backend.Repository.HazardRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class HazardService {

    private final HazardRepository hazardRepository;

    // Add a new hazard
    public HazardsDTO addHazards(HazardsDTO hazardsDTO) {
        try {
            Hazard hazard = HazardsMapper.toEntity(hazardsDTO);

            Hazard savedHazard = hazardRepository.save(hazard);

            return HazardsMapper.toDTO(savedHazard);

        } catch (Exception e) {
            log.error("Failed to add hazard", e);
            throw new RuntimeException("Failed to add hazard", e);
        }
    }

    // Get hazards by city or all cities
    public List<HazardsResponseDTO> getHazardsByCity(String city) {

        List<Hazard> hazards;

        if (city == null || city.isBlank() || city.equalsIgnoreCase("all")) {
            log.info("Fetching hazards from all cities");
            hazards = hazardRepository.findAll();
        } else {
            log.info("Fetching hazards for city: {}", city);
            hazards = hazardRepository.findByCityContainingIgnoreCase(city);
        }

        return hazards.stream()
                .map(HazardsMapper::toResponseDTO)
                .toList();
    }

    // Get unverified hazards by city or all cities
    public List<HazardsResponseDTO> getUnverifiedHazardsByCity(String city) {

        List<Hazard> hazards;

        if (city == null || city.isBlank() || city.equalsIgnoreCase("all")) {
            log.info("Fetching unverified hazards from all cities");
            hazards = hazardRepository.findByVerifiedFalse();
        } else {
            log.info("Fetching unverified hazards for city: {}", city);
            hazards = hazardRepository.findByCityContainingIgnoreCaseAndVerifiedFalse(city);
        }

        return hazards.stream()
                .map(HazardsMapper::toResponseDTO)
                .toList();
    }

    // Verify a single hazard
    public void verifyHazards(String id) {

        Hazard hazard = hazardRepository.findByIdAndVerifiedFalse(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Hazard not found or already verified: " + id
                        )
                );

        hazard.setVerified(true);

        hazardRepository.save(hazard);
    }

    // Verify multiple hazards
    public void verifyMultipleHazards(List<String> ids) {

        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("Hazard ID list cannot be empty");
        }

        List<Hazard> hazards = hazardRepository.findByIdIn(ids);

        hazards.forEach(h -> {
            if (Boolean.FALSE.equals(h.getVerified())) {
                h.setVerified(true);
            }
        });

        hazardRepository.saveAll(hazards);
    }

    // Delete a single hazard
    public void deleteHazard(String id) {

        Hazard hazard = hazardRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Hazard not found with id: " + id
                        )
                );

        hazardRepository.delete(hazard);
    }

    // Delete multiple hazards
    public void deleteHazardsBulk(List<String> ids) {

        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("Hazard ID list cannot be empty");
        }

        List<Hazard> hazards = hazardRepository.findByIdIn(ids);

        if (hazards.isEmpty()) {
            throw new IllegalArgumentException("No hazards found for provided IDs");
        }

        hazardRepository.deleteAll(hazards);
    }
}
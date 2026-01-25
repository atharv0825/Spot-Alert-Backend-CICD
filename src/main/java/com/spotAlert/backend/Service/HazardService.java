package com.spotAlert.backend.Service;

import com.spotAlert.backend.DTO.HazardsDTO;
import com.spotAlert.backend.DTO.HazardsResponseDTO;
import com.spotAlert.backend.Entity.Hazard;
import com.spotAlert.backend.Mapper.HazardsMapper;
import com.spotAlert.backend.Repository.HazardRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class HazardService {

    private final HazardRepository hazardRepository;

    public HazardsDTO addHazards(HazardsDTO hazardsDTO) {
        try {
            Hazard hazard = HazardsMapper.toEntity(hazardsDTO);
            return HazardsMapper.toDTO(hazardRepository.save(hazard));
        } catch (Exception e) {
            throw new RuntimeException("Failed to add hazard", e);
        }
    }

    public List<HazardsResponseDTO> getHazardsByCity(String city) {
        if (city == null || city.isBlank()) return List.of();

        return hazardRepository.findByCityContainingIgnoreCase(city)
                .stream()
                .map(HazardsMapper::toResponseDTO)
                .toList();
    }

    public List<HazardsResponseDTO> getUnverifiedHazardsByCity(String city) {
        if (city == null || city.isBlank()) return List.of();

        return hazardRepository.findByCityContainingIgnoreCaseAndVerifiedFalse(city)
                .stream()
                .map(HazardsMapper::toResponseDTO)
                .toList();
    }

    public void verifyHazards(String id) {
        Hazard hazard = hazardRepository.findByIdAndVerifiedFalse(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Hazard not found or already verified: " + id));

        hazard.setVerified(true);
        hazardRepository.save(hazard);
    }

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

    public void deleteHazard(String id) {
        Hazard hazard = hazardRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Hazard not found with id: " + id));

        hazardRepository.delete(hazard);
    }

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

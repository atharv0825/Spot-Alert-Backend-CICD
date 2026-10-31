package com.spotAlert.backend.Repository;

import com.spotAlert.backend.Entity.Hazard;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HazardRepository extends MongoRepository<Hazard, String> {

    // Find hazards near a location
    List<Hazard> findByLocationNear(Point location, Distance distance);

    // Find hazards within a geographical bounding box
    @Query("""
        {
          location: {
            $geoWithin: {
              $box: [
                [ ?0, ?1 ],
                [ ?2, ?3 ]
              ]
            }
          }
        }
    """)
    List<Hazard> findWithinBoundingBox(
            double minLng,
            double minLat,
            double maxLng,
            double maxLat
    );

    // Find hazards by city
    List<Hazard> findByCityContainingIgnoreCase(String city);

    // Find all unverified hazards
    List<Hazard> findByVerifiedFalse();

    // Find unverified hazards by city
    List<Hazard> findByCityContainingIgnoreCaseAndVerifiedFalse(String city);

    // Find a specific unverified hazard
    Optional<Hazard> findByIdAndVerifiedFalse(String id);

    // Find multiple hazards by IDs
    List<Hazard> findByIdIn(List<String> ids);
}
package com.spotAlert.backend.Health;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MongoHealthIndicator implements HealthIndicator {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Health health() {
        try {

            Document commandResult = mongoTemplate.executeCommand("{ ping: 1 }");

            if (commandResult != null) {
                return Health.up()
                        .withDetail("database", "MongoDB Atlas")
                        .withDetail("status", "Connected")
                        .build();
            } else {
                return Health.down()
                        .withDetail("error", "Ping command returned null")
                        .build();
            }
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("database", "MongoDB Atlas")
                    .withDetail("status", "Connection Failed")
                    .build();
        }
    }
}

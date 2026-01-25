package com.spotAlert.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpotAlertApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotAlertApplication.class, args);
	}

}

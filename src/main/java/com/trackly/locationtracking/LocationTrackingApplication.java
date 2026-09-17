package com.trackly.locationtracking;

import com.trackly.locationtracking.stop.StopDetectionProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StopDetectionProperties.class)
public class LocationTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationTrackingApplication.class, args);
    }
}

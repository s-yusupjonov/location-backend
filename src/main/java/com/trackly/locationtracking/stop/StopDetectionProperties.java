package com.trackly.locationtracking.stop;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tracking.stop-detection")
public record StopDetectionProperties(int radiusMeters, int minDurationMinutes) {
}

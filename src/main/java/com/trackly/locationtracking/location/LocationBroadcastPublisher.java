package com.trackly.locationtracking.location;

import com.trackly.locationtracking.location.dto.LocationPingResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class LocationBroadcastPublisher {

    private static final String TOPIC_TEMPLATE = "/topic/locations/%d";

    private final SimpMessagingTemplate messagingTemplate;

    public LocationBroadcastPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publish(Long employeeId, LocationPingResponse locationPingResponse) {
        messagingTemplate.convertAndSend(TOPIC_TEMPLATE.formatted(employeeId), locationPingResponse);
    }
}

package com.trackly.locationtracking.geocoding;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Locale;
import java.util.Optional;

@Component
public class NominatimGeocodingClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NominatimGeocodingClient.class);
    private static final String ACCEPT_LANGUAGE = "uz,ru,en";

    private final RestClient restClient;
    private final String contactEmail;

    public NominatimGeocodingClient(RestClient.Builder restClientBuilder,
                                    @Value("${geocoding.nominatim.base-url}") String baseUrl,
                                    @Value("${geocoding.nominatim.contact-email:}") String contactEmail) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader("User-Agent", "employee-location-tracking-service/1.0")
                .build();
        this.contactEmail = contactEmail;
    }

    public String reverseGeocode(double latitude, double longitude) {
        try {
            JsonNode response = restClient.get()
                    .uri(uriBuilder -> {
                        uriBuilder
                                .queryParam("lat", latitude)
                                .queryParam("lon", longitude)
                                .queryParam("format", "json")
                                .queryParam("addressdetails", 1)
                                .queryParam("zoom", 18)
                                .queryParam("accept-language", ACCEPT_LANGUAGE);
                        if (contactEmail != null && !contactEmail.isBlank()) {
                            uriBuilder.queryParam("email", contactEmail);
                        }
                        return uriBuilder.build();
                    })
                    .retrieve()
                    .body(JsonNode.class);

            return extractAddress(response).orElseGet(() -> formatCoordinates(latitude, longitude));
        } catch (RestClientException exception) {
            LOGGER.warn("Reverse geocoding failed for coordinates {}, {}: {}", latitude, longitude, exception.getMessage());
            return formatCoordinates(latitude, longitude);
        }
    }

    private Optional<String> extractAddress(JsonNode response) {
        if (response == null || response.path("address").isMissingNode()) {
            return Optional.empty();
        }

        JsonNode address = response.path("address");

        String road = firstNonBlank(text(address, "road"), text(address, "pedestrian"), text(address, "footway"));
        String houseNumber = text(address, "house_number");
        String place = firstNonBlank(
                text(address, "city"), text(address, "town"), text(address, "village"),
                text(address, "municipality"), text(address, "suburb"),
                text(address, "county"), text(address, "state"));

        if (road == null && place == null) {
            String displayName = text(response, "display_name");
            return Optional.ofNullable(displayName).map(AddressLocalizer::transliterate);
        }

        StringBuilder result = new StringBuilder();
        if (road != null) {
            result.append(AddressLocalizer.localizeStreet(road));
            if (houseNumber != null) {
                result.append(' ').append(AddressLocalizer.transliterate(houseNumber));
            }
        } else if (houseNumber != null) {
            result.append(AddressLocalizer.transliterate(houseNumber));
        }

        if (place != null) {
            if (!result.isEmpty()) {
                result.append(", ");
            }
            result.append(AddressLocalizer.localizeLocality(place));
        }

        return result.isEmpty() ? Optional.empty() : Optional.of(result.toString());
    }

    private String text(JsonNode node, String field) {
        JsonNode value = node.path(field);
        return value.isMissingNode() || value.isNull() ? null : value.asText(null);
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    private String formatCoordinates(double latitude, double longitude) {
        return String.format(Locale.ROOT, "%.6f, %.6f", latitude, longitude);
    }
}
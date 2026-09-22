package com.trackly.locationtracking.geocoding;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Component
public class YandexGeocodingClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(YandexGeocodingClient.class);
    private static final String RESPONSE_LANGUAGE = "ru_RU";

    private final RestClient restClient;
    private final String apiKey;

    public YandexGeocodingClient(RestClient.Builder restClientBuilder,
                                 @Value("${geocoding.yandex.base-url}") String baseUrl,
                                 @Value("${geocoding.yandex.api-key}") String apiKey) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public String reverseGeocode(double latitude, double longitude) {
        if (apiKey.isBlank()) {
            LOGGER.warn("Yandex geocoder API key is not configured, falling back to raw coordinates");
            return formatCoordinates(latitude, longitude);
        }

        try {
            JsonNode response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("apikey", apiKey)
                            .queryParam("geocode", longitude + "," + latitude)
                            .queryParam("lang", RESPONSE_LANGUAGE)
                            .queryParam("format", "json")
                            .queryParam("results", 1)
                            .build())
                    .retrieve()
                    .body(JsonNode.class);

            return extractAddress(response).orElseGet(() -> formatCoordinates(latitude, longitude));
        } catch (RestClientException exception) {
            LOGGER.warn("Reverse geocoding failed for coordinates {}, {}: {}", latitude, longitude, exception.getMessage());
            return formatCoordinates(latitude, longitude);
        }
    }

    private Optional<String> extractAddress(JsonNode response) {
        if (response == null) {
            return Optional.empty();
        }

        JsonNode featureMembers = response.path("response").path("GeoObjectCollection").path("featureMember");
        if (!featureMembers.isArray() || featureMembers.isEmpty()) {
            return Optional.empty();
        }

        JsonNode metaData = featureMembers.get(0).path("GeoObject").path("metaDataProperty").path("GeocoderMetaData");
        JsonNode components = metaData.path("Address").path("Components");

        Optional<String> structured = buildFromComponents(components);
        if (structured.isPresent()) {
            return structured;
        }

        String text = metaData.path("text").asText(null);
        return Optional.ofNullable(text).map(AddressLocalizer::transliterate);
    }

    private Optional<String> buildFromComponents(JsonNode components) {
        if (!components.isArray() || components.isEmpty()) {
            return Optional.empty();
        }

        Map<String, String> byKind = new LinkedHashMap<>();
        for (JsonNode component : components) {
            String kind = component.path("kind").asText(null);
            String name = component.path("name").asText(null);
            if (kind != null && name != null) {
                byKind.put(kind, name);
            }
        }

        String street = byKind.get("street");
        String house = byKind.get("house");
        String place = firstNonBlank(byKind.get("locality"), byKind.get("district"), byKind.get("area"),
                byKind.get("province"));

        if (street == null && place == null) {
            return Optional.empty();
        }

        StringBuilder result = new StringBuilder();
        if (street != null) {
            result.append(AddressLocalizer.localizeStreet(street));
            if (house != null) {
                result.append(' ').append(AddressLocalizer.transliterate(house));
            }
        } else if (house != null) {
            result.append(AddressLocalizer.transliterate(house));
        }

        if (place != null) {
            if (!result.isEmpty()) {
                result.append(", ");
            }
            result.append(AddressLocalizer.localizeLocality(place));
        }

        return result.isEmpty() ? Optional.empty() : Optional.of(result.toString());
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
package backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import backend.dto.cwa.CwaWeatherResponse;

@Component
public class WeatherApiClient {

    private final RestClient restClient;
    private final String apiKey;

    public WeatherApiClient(
            RestClient restClient,
            @Value("${weather.api.key}") String apiKey) {

        this.restClient = restClient;
        this.apiKey = apiKey;
    }

    public CwaWeatherResponse getWeather(String city) {

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/rest/datastore/F-C0032-001")
                        .queryParam("Authorization", apiKey)
                        .queryParam("locationName", city)
                        .build())
                .retrieve()
                .body(CwaWeatherResponse.class);
    }
}

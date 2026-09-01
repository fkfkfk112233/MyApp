package backend.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import backend.dto.GeocodeResponse;

@Component
public class GeocodeApiClient {

	private final RestClient restClient;
	private final String apiKey;

	public GeocodeApiClient(
			@Qualifier("geocodeRestClient") RestClient restClient, 
			@Value("${geocode.api.key}") String apiKey) {

		this.restClient = restClient;
		this.apiKey = apiKey;
	}

	public GeocodeResponse reverseGeocode(Double latitude, Double longitude) {

		return restClient
				.get().uri(uriBuilder -> uriBuilder.path("/reverse").queryParam("lat", latitude)
						.queryParam("lon", longitude).queryParam("api_key", apiKey).build())
				.retrieve().body(GeocodeResponse.class);
	}
}
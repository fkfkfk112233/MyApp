package backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient cwaRestClient() {
        return RestClient.builder()
                .baseUrl("https://opendata.cwa.gov.tw")
                .build();
    }
    
    @Bean
    public RestClient geocodeRestClient() {
        return RestClient.builder()
                .baseUrl("https://geocode.maps.co")
                .build();
    }
}

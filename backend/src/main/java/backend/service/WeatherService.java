package backend.service;

import org.springframework.stereotype.Service;

import backend.client.WeatherApiClient;
import backend.dto.WeatherResponse;
import backend.dto.cwa.CwaWeatherResponse;

@Service
public class WeatherService {

    private final WeatherApiClient weatherApiClient;

    public WeatherService(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    public WeatherResponse getWeather(String city) {

        CwaWeatherResponse cwaResponse =
                weatherApiClient.getWeather(city);

        CwaWeatherResponse.Location location =
                cwaResponse.getRecords()
                        .getLocation()
                        .get(0);

        String weather = getParameter(
                location,
                "Wx"
        ).getParameterName();

        String weatherCode = getParameter(
                location,
                "Wx"
        ).getParameterValue();

        int minTemperature = Integer.parseInt(
                getParameter(location, "MinT")
                        .getParameterName()
        );

        int maxTemperature = Integer.parseInt(
                getParameter(location, "MaxT")
                        .getParameterName()
        );

        int rainProbability = Integer.parseInt(
                getParameter(location, "PoP")
                        .getParameterName()
        );

        return new WeatherResponse(
                location.getLocationName(),
                weather,
                weatherCode,
                minTemperature,
                maxTemperature,
                rainProbability
        );
    }

    private CwaWeatherResponse.Parameter getParameter(
            CwaWeatherResponse.Location location,
            String elementName) {

        return location.getWeatherElement()
                .stream()
                .filter(element ->
                        element.getElementName()
                                .equals(elementName))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "找不到天氣資料: " + elementName
                        ))
                .getTime()
                .get(0)
                .getParameter();
    }
}
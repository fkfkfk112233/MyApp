package backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.WeatherForecastResponse;
import backend.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:5173")
public class WeatherController {

	private final WeatherService weatherService;

	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@GetMapping
	public WeatherForecastResponse getWeather(@RequestParam(required = false) String city,
			@RequestParam(required = false) Double latitude, @RequestParam(required = false) Double longitude) {

		if (city != null) {
			return weatherService.getWeatherForecast(city);
		}

		if (latitude != null && longitude != null) {
			return weatherService.getWeatherForecast(latitude, longitude);
		}

		throw new IllegalArgumentException("必須提供 city 或 latitude / longitude");
	}
}
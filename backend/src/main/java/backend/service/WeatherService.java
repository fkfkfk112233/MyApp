package backend.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import backend.client.GeocodeApiClient;
import backend.client.WeatherApiClient;
import backend.dto.WeatherResponse;
import backend.dto.cwa.CwaWeatherResponse;

@Service
public class WeatherService {

	private final WeatherApiClient weatherApiClient;
	private final GeocodeApiClient geocodeApiClient;

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public WeatherService(
			WeatherApiClient weatherApiClient,
			GeocodeApiClient geocodeApiClient) {
		this.weatherApiClient = weatherApiClient;
		this.geocodeApiClient = geocodeApiClient;
	}

	public WeatherResponse getWeather(String city) {

		CwaWeatherResponse cwaResponse = weatherApiClient.getWeather(city);

		CwaWeatherResponse.Location location = cwaResponse.getRecords().getLocation().get(0);

		LocalDateTime now = LocalDateTime.now();

		CwaWeatherResponse.Time weatherTime = findCurrentTime(location, "Wx", now);

		CwaWeatherResponse.Time popTime = findCurrentTime(location, "PoP", now);

		CwaWeatherResponse.Time minTTime = findCurrentTime(location, "MinT", now);

		CwaWeatherResponse.Time maxTTime = findCurrentTime(location, "MaxT", now);

		String weather = weatherTime.getParameter().getParameterName();

		String weatherCode = weatherTime.getParameter().getParameterValue();

		int rainProbability = Integer.parseInt(popTime.getParameter().getParameterName());

		int minTemperature = Integer.parseInt(minTTime.getParameter().getParameterName());

		int maxTemperature = Integer.parseInt(maxTTime.getParameter().getParameterName());

		return new WeatherResponse(location.getLocationName(), weather, weatherCode, minTemperature, maxTemperature,
				rainProbability, weatherTime.getStartTime(), weatherTime.getEndTime());
	}

	private CwaWeatherResponse.Time findCurrentTime(CwaWeatherResponse.Location location, String elementName,
			LocalDateTime now) {

		CwaWeatherResponse.WeatherElement element = location.getWeatherElement().stream()
				.filter(weatherElement -> weatherElement.getElementName().equals(elementName)).findFirst()
				.orElseThrow(() -> new IllegalStateException("找不到天氣資料: " + elementName));

		List<CwaWeatherResponse.Time> times = element.getTime();

		return times.stream().filter(time -> {

			LocalDateTime start = LocalDateTime.parse(time.getStartTime(), formatter);

			LocalDateTime end = LocalDateTime.parse(time.getEndTime(), formatter);

			return !now.isBefore(start) && now.isBefore(end);
		}).findFirst().orElseThrow(() -> new IllegalStateException("找不到目前時間對應的預報時段"));
	}

	public WeatherResponse getWeather(Double latitude, Double longitude) {

		// 暫時測試 GPS 是否成功傳到 Service
		System.out.println("GPS latitude: " + latitude);
		System.out.println("GPS longitude: " + longitude);

		throw new UnsupportedOperationException("GPS weather 尚未實作");
	}
}
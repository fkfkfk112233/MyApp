package backend.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import backend.client.GeocodeApiClient;
import backend.client.WeatherApiClient;
import backend.dto.ForecastResponse;
import backend.dto.GeocodeResponse;
import backend.dto.WeatherForecastResponse;
import backend.dto.WeatherResponse;
import backend.dto.cwa.CwaWeatherResponse;

@Service
public class WeatherService {

	private final WeatherApiClient weatherApiClient;
	private final GeocodeApiClient geocodeApiClient;

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public WeatherService(WeatherApiClient weatherApiClient, GeocodeApiClient geocodeApiClient) {
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
		}).findFirst().orElseGet(() -> times.stream().filter(time -> {
			LocalDateTime start = LocalDateTime.parse(time.getStartTime(), formatter);

			return start.isAfter(now);
		}).findFirst().orElseThrow(() -> new IllegalStateException("找不到目前或下一個預報時段")));
	}

	public WeatherResponse getWeather(Double latitude, Double longitude) {

		GeocodeResponse response = geocodeApiClient.reverseGeocode(latitude, longitude);

		String city = response.getAddress().getCity();

		System.out.println("GPS 對應城市: " + city);

		return getWeather(city);
	}

	private List<ForecastResponse> getForecasts(CwaWeatherResponse.Location location) {

		CwaWeatherResponse.WeatherElement wxElement = getWeatherElement(location, "Wx");

		CwaWeatherResponse.WeatherElement popElement = getWeatherElement(location, "PoP");

		CwaWeatherResponse.WeatherElement minTElement = getWeatherElement(location, "MinT");

		CwaWeatherResponse.WeatherElement maxTElement = getWeatherElement(location, "MaxT");

		List<CwaWeatherResponse.Time> wxTimes = wxElement.getTime();

		List<CwaWeatherResponse.Time> popTimes = popElement.getTime();

		List<CwaWeatherResponse.Time> minTTimes = minTElement.getTime();

		List<CwaWeatherResponse.Time> maxTTimes = maxTElement.getTime();

		List<ForecastResponse> forecasts = new ArrayList<>();

		for (CwaWeatherResponse.Time wxTime : wxTimes) {

			String startTime = wxTime.getStartTime();
			String endTime = wxTime.getEndTime();

			CwaWeatherResponse.Time popTime = findSamePeriod(popTimes, startTime, endTime);

			CwaWeatherResponse.Time minTTime = findSamePeriod(minTTimes, startTime, endTime);

			CwaWeatherResponse.Time maxTTime = findSamePeriod(maxTTimes, startTime, endTime);

			String weather = wxTime.getParameter().getParameterName();

			String weatherCode = wxTime.getParameter().getParameterValue();

			int rainProbability = Integer.parseInt(popTime.getParameter().getParameterName());

			int minTemperature = Integer.parseInt(minTTime.getParameter().getParameterName());

			int maxTemperature = Integer.parseInt(maxTTime.getParameter().getParameterName());

			forecasts.add(new ForecastResponse(weather, weatherCode, minTemperature, maxTemperature, rainProbability,
					startTime, endTime));
		}

		return forecasts;
	}

	private CwaWeatherResponse.WeatherElement getWeatherElement(CwaWeatherResponse.Location location,
			String elementName) {

		return location.getWeatherElement().stream()
				.filter(weatherElement -> weatherElement.getElementName().equals(elementName)).findFirst()
				.orElseThrow(() -> new IllegalStateException("找不到天氣資料: " + elementName));
	}

	private CwaWeatherResponse.Time findSamePeriod(List<CwaWeatherResponse.Time> times, String startTime,
			String endTime) {

		return times.stream().filter(time -> time.getStartTime().equals(startTime) && time.getEndTime().equals(endTime))
				.findFirst().orElseThrow(() -> new IllegalStateException("找不到相同預報時段: " + startTime + " ~ " + endTime));
	}

	public WeatherForecastResponse getWeatherForecast(String city) {

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

		WeatherResponse current = new WeatherResponse(location.getLocationName(), weather, weatherCode, minTemperature,
				maxTemperature, rainProbability, weatherTime.getStartTime(), weatherTime.getEndTime());

		List<ForecastResponse> forecasts = getForecasts(location);

		return new WeatherForecastResponse(current, forecasts);
	}

	public WeatherForecastResponse getWeatherForecast(Double latitude, Double longitude) {

		GeocodeResponse response = geocodeApiClient.reverseGeocode(latitude, longitude);

		String city = response.getAddress().getCity();

		System.out.println("GPS 對應城市: " + city);

		return getWeatherForecast(city);
	}
}
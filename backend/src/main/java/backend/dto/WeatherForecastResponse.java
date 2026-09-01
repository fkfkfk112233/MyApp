package backend.dto;

import java.util.List;

public class WeatherForecastResponse {

	private WeatherResponse current;
	private List<ForecastResponse> forecasts;

	public WeatherForecastResponse(WeatherResponse current, List<ForecastResponse> forecasts) {

		this.current = current;
		this.forecasts = forecasts;
	}

	public WeatherResponse getCurrent() {
		return current;
	}

	public List<ForecastResponse> getForecasts() {
		return forecasts;
	}
}
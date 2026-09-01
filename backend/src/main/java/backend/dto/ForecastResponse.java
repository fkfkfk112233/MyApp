package backend.dto;

public class ForecastResponse {

	private String weather;
	private String weatherCode;
	private int minTemperature;
	private int maxTemperature;
	private int rainProbability;
	private String startTime;
	private String endTime;

	public ForecastResponse(String weather, String weatherCode, int minTemperature, int maxTemperature,
			int rainProbability, String startTime, String endTime) {

		this.weather = weather;
		this.weatherCode = weatherCode;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		this.rainProbability = rainProbability;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getWeather() {
		return weather;
	}

	public String getWeatherCode() {
		return weatherCode;
	}

	public int getMinTemperature() {
		return minTemperature;
	}

	public int getMaxTemperature() {
		return maxTemperature;
	}

	public int getRainProbability() {
		return rainProbability;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}
}
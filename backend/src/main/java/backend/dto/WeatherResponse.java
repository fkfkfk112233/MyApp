package backend.dto;

public class WeatherResponse {

    private String city;
    private String weather;
    private String weatherCode;
    private int minTemperature;
    private int maxTemperature;
    private int rainProbability;

    public WeatherResponse() {
    }

    public WeatherResponse(
            String city,
            String weather,
            String weatherCode,
            int minTemperature,
            int maxTemperature,
            int rainProbability) {

        this.city = city;
        this.weather = weather;
        this.weatherCode = weatherCode;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.rainProbability = rainProbability;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public int getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(int rainProbability) {
        this.rainProbability = rainProbability;
    }
}

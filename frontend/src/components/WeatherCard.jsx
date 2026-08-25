import "./WeatherCard.css";

function WeatherCard({ weather }) {
  const weatherIconUrl = `https://www.cwa.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/${weather.weatherCode}.svg`;

  return (
    <div className="weather-card">
      <h2 className="weather-card-title">天氣預報</h2>

      <div className="weather-card-body">
        <div className="weather-location">
          <span>目前地點</span>
          <strong>{weather.city}</strong>
        </div>

        <div className="weather-period">
          <span>預報時段</span>
          <strong>
            {weather.startTime} ～ {weather.endTime}
          </strong>
        </div>

        <div className="weather-icon-box">
          <img
            src={weatherIconUrl}
            alt={weather.weather}
            className="weather-icon"
          />
        </div>

        <div className="weather-status">
          <h3>{weather.weather}</h3>
        </div>

        <div className="temperature">
          <span>{weather.minTemperature}°C</span>
          <span>~</span>
          <span>{weather.maxTemperature}°C</span>
        </div>

        <div className="rain-probability">
          降雨機率：{weather.rainProbability}%
        </div>
      </div>
    </div>
  );
}

export default WeatherCard;

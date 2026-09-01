import "./WeatherCard.css";

function WeatherCard({ weather, loading, error }) {
  if (loading) {
    return (
      <section className="current-weather-card">
        <div className="weather-loading">
          <div className="loading-spinner"></div>
          <p>正在取得天氣資料...</p>
        </div>
      </section>
    );
  }

  if (error) {
    return (
      <section className="current-weather-card">
        <div className="weather-error">
          <div className="error-icon">⚠️</div>

          <h2>無法取得天氣</h2>

          <p>{error}</p>
        </div>
      </section>
    );
  }

  if (!weather) {
    return (
      <section className="current-weather-card">
        <div className="weather-empty">
          <div>☁️</div>
          <p>等待天氣資料...</p>
        </div>
      </section>
    );
  }

  const current = weather.current;

  const weatherIconUrl = `https://www.cwa.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/${current.weatherCode}.svg`;

  return (
    <section className="current-weather-card">
      <div className="current-weather-header">
        <div className="card-title">
          <div className="card-title-icon current-title-icon">☀️</div>

          <div>
            <h2>目前天氣</h2>
            <p>{current.city}</p>
          </div>
        </div>

        <div className="forecast-time-badge">
          <span>◷</span>

          <span>
            {current.startTime.slice(11, 16)}
            {" ~ "}
            {current.endTime.slice(11, 16)}
          </span>
        </div>
      </div>

      <div className="main-weather-content">
        <div className="weather-icon-box">
          <img
            src={weatherIconUrl}
            alt={current.weather}
            className="weather-icon"
          />
        </div>

        <h3>{current.weather}</h3>

        <div className="main-temperature">{current.maxTemperature}°C</div>

        <div className="temperature-range">
          <span>最低 {current.minTemperature}°C</span>

          <span className="temperature-separator">•</span>

          <span>最高 {current.maxTemperature}°C</span>
        </div>
      </div>

      <div className="weather-info-grid">
        <div className="weather-info-item">
          <span className="weather-info-icon">💧</span>

          <div>
            <span>降雨機率</span>

            <strong>{current.rainProbability}%</strong>
          </div>
        </div>

        <div className="weather-info-item">
          <span className="weather-info-icon">🌡️</span>

          <div>
            <span>最高溫度</span>

            <strong>{current.maxTemperature}°C</strong>
          </div>
        </div>

        <div className="weather-info-item">
          <span className="weather-info-icon">❄️</span>

          <div>
            <span>最低溫度</span>

            <strong>{current.minTemperature}°C</strong>
          </div>
        </div>
      </div>

      <div className="current-period">
        <span>◷</span>

        <span>預報時段：</span>

        <strong>
          {current.startTime}
          <br />～ {current.endTime}
        </strong>
      </div>
    </section>
  );
}

export default WeatherCard;

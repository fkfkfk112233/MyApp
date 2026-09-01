function ForecastCard({ weather, loading, error }) {
  if (loading) {
    return (
      <section className="forecast-card">
        <div className="card-title">
          <div className="card-title-icon forecast-title-icon">📅</div>

          <div>
            <h2>未來預報</h2>
            <p>36 小時天氣預報</p>
          </div>
        </div>

        <div className="forecast-loading">
          <div className="loading-spinner"></div>
          <p>正在取得預報...</p>
        </div>
      </section>
    );
  }

  if (error) {
    return (
      <section className="forecast-card">
        <div className="card-title">
          <div className="card-title-icon forecast-title-icon">📅</div>

          <div>
            <h2>未來預報</h2>
            <p>36 小時天氣預報</p>
          </div>
        </div>

        <div className="forecast-error">
          <span>⚠️</span>
          <p>暫時無法取得預報資料</p>
        </div>
      </section>
    );
  }

  if (!weather) {
    return (
      <section className="forecast-card">
        <div className="card-title">
          <div className="card-title-icon forecast-title-icon">📅</div>

          <div>
            <h2>未來預報</h2>
            <p>36 小時天氣預報</p>
          </div>
        </div>

        <div className="forecast-empty">
          <span>☁️</span>
          <p>等待預報資料...</p>
        </div>
      </section>
    );
  }

  const forecasts = (weather.forecasts || []).slice(1);

  const formatTime = (dateTime) => {
    if (!dateTime) {
      return "--:--";
    }

    return dateTime.slice(11, 16);
  };

  const getWeatherIconUrl = (weatherCode) => {
    return `https://www.cwa.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/${weatherCode}.svg`;
  };

  return (
    <section className="forecast-card">
      <div className="card-title">
        <div className="card-title-icon forecast-title-icon">📅</div>

        <div>
          <h2>未來預報</h2>
          <p>{weather.current.city} 36 小時預報</p>
        </div>
      </div>

      <div className="forecast-list">
        {forecasts.map((forecast, index) => (
          <div
            className={`forecast-item ${
              index === 0 ? "forecast-item-active" : ""
            }`}
            key={`${forecast.startTime}-${forecast.endTime}`}
          >
            <div className="forecast-item-header">
              <span className="forecast-time">
                {formatTime(forecast.startTime)}
                {" ~ "}
                {formatTime(forecast.endTime)}
              </span>

              {index === 0 && <span className="forecast-next">接下來</span>}
            </div>

            <div className="forecast-item-content">
              <div className="forecast-placeholder-icon">
                <img
                  src={getWeatherIconUrl(forecast.weatherCode)}
                  alt={forecast.weather}
                  className="forecast-weather-icon"
                />
              </div>

              <div className="forecast-detail">
                <strong>{forecast.weather}</strong>

                <div className="forecast-temperature">
                  <span>{forecast.minTemperature}°C</span>

                  <span>~</span>

                  <span>{forecast.maxTemperature}°C</span>
                </div>

                <span className="forecast-rain">
                  💧 降雨機率 {forecast.rainProbability}%
                </span>
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="forecast-footer">
        <span>◉</span>
        <span>資料來源：中央氣象署 CWA</span>
      </div>
    </section>
  );
}

export default ForecastCard;

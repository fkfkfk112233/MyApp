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

  const formatTime = (dateTime) => {
    if (!dateTime) {
      return "--:--";
    }

    return dateTime.slice(11, 16);
  };

  return (
    <section className="forecast-card">
      <div className="card-title">
        <div className="card-title-icon forecast-title-icon">📅</div>

        <div>
          <h2>未來預報</h2>
          <p>{weather.city} 36 小時預報</p>
        </div>
      </div>

      <div className="forecast-list">
        <div className="forecast-item forecast-item-active">
          <div className="forecast-item-header">
            <span className="forecast-time">
              {formatTime(weather.startTime)}
              {" ~ "}
              {formatTime(weather.endTime)}
            </span>

            <span className="forecast-next">接下來</span>
          </div>

          <div className="forecast-item-content">
            <div className="forecast-placeholder-icon">☀️</div>

            <div className="forecast-detail">
              <strong>{weather.weather}</strong>

              <div className="forecast-temperature">
                <span>{weather.minTemperature}°C</span>
                <span>~</span>
                <span>{weather.maxTemperature}°C</span>
              </div>

              <span className="forecast-rain">
                💧 降雨機率 {weather.rainProbability}%
              </span>
            </div>
          </div>
        </div>

        <div className="forecast-item forecast-item-placeholder">
          <div className="forecast-item-header">
            <span className="forecast-time">下一時段</span>
          </div>

          <div className="forecast-placeholder-content">
            <span>🌙</span>
            <div>
              <strong>下一個預報時段</strong>
              <p>即將接上 CWA 36 小時資料</p>
            </div>
          </div>
        </div>

        <div className="forecast-item forecast-item-placeholder">
          <div className="forecast-item-header">
            <span className="forecast-time">後續時段</span>
          </div>

          <div className="forecast-placeholder-content">
            <span>☁️</span>
            <div>
              <strong>後續預報</strong>
              <p>即將接上 CWA 36 小時資料</p>
            </div>
          </div>
        </div>
      </div>

      <div className="forecast-footer">
        <span>◉</span>
        <span>資料來源：中央氣象署 CWA</span>
      </div>
    </section>
  );
}

export default ForecastCard;

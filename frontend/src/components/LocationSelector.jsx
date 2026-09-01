import { useState } from "react";

function LocationSelector({
  selectedCity,
  setSelectedCity,
  location,
  setLocation,
  weather,
}) {
  const [locating, setLocating] = useState(false);
  const [locationError, setLocationError] = useState("");

  function handleChange(event) {
    const city = event.target.value;

    setSelectedCity(city);

    setLocation({
      type: "city",
      city,
      latitude: null,
      longitude: null,
    });

    setLocationError("");
  }

  const getCurrentLocation = () => {
    if (!navigator.geolocation) {
      setLocationError("您的瀏覽器不支援定位功能");
      return;
    }

    setLocating(true);
    setLocationError("");

    navigator.geolocation.getCurrentPosition(
      (position) => {
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;

        setLocation({
          type: "gps",
          city: null,
          latitude,
          longitude,
        });

        setLocating(false);
      },
      (error) => {
        console.error("取得 GPS 失敗:", error);

        setLocating(false);

        switch (error.code) {
          case error.PERMISSION_DENIED:
            setLocationError("您拒絕了位置權限");
            break;

          case error.POSITION_UNAVAILABLE:
            setLocationError("目前無法取得您的位置");
            break;

          case error.TIMEOUT:
            setLocationError("取得位置逾時，請再試一次");
            break;

          default:
            setLocationError("無法取得目前位置");
        }
      },
    );
  };

  const formatCoordinate = (value) => {
    if (value === null || value === undefined) {
      return "--";
    }

    return value.toFixed(6);
  };

  const isGps = location?.type === "gps";

  return (
    <section className="location-card">
      <div className="card-title">
        <div className="card-title-icon location-title-icon">📍</div>

        <div>
          <h2>選擇地點</h2>
          <p>選擇或取得您要查詢的地點</p>
        </div>
      </div>

      <div className="location-form">
        <label htmlFor="city-select">選擇地點</label>

        <div className="select-wrapper">
          <select id="city-select" value={selectedCity} onChange={handleChange}>
            <option value="臺北市">🏙️ 臺北市</option>
            <option value="新北市">🏙️ 新北市</option>
            <option value="桃園市">🏙️ 桃園市</option>
            <option value="臺中市">🏙️ 臺中市</option>
            <option value="高雄市">🏙️ 高雄市</option>
          </select>
        </div>

        <button
          className="gps-button"
          onClick={getCurrentLocation}
          disabled={locating}
        >
          <span className="gps-button-icon">{locating ? "◌" : "⌾"}</span>

          {locating ? "定位中..." : "使用目前位置"}
        </button>
      </div>

      {locationError && (
        <div className="location-error">
          <span>⚠️</span>
          <span>{locationError}</span>
        </div>
      )}

      <div className="location-divider"></div>

      <div className="current-location-section">
        <div className="section-label">
          <span>📍</span>
          <span>目前位置</span>
        </div>

        <div className="current-location-box">
          <div className="current-location-main">
            <div className="location-pin">●</div>

            <div>
              <strong>
                {isGps
                  ? weather?.current?.city || "取得位置中..."
                  : selectedCity}
              </strong>

              {/* <span>{isGps ? "GPS 定位" : "手動選擇"}</span> */}
            </div>
          </div>

          <div
            className={`location-source ${
              isGps ? "gps-source" : "manual-source"
            }`}
          >
            {isGps ? "GPS 定位" : "手動"}
          </div>
        </div>
      </div>

      <div className="coordinates-section">
        <div className="section-label coordinate-label">
          <span>⌖</span>
          <span>座標資訊</span>
        </div>

        <div className="coordinate-grid">
          <div className="coordinate-box">
            <span className="coordinate-name">緯度 (Latitude)</span>

            <strong>{formatCoordinate(location?.latitude)}</strong>
          </div>

          <div className="coordinate-box">
            <span className="coordinate-name">經度 (Longitude)</span>

            <strong>{formatCoordinate(location?.longitude)}</strong>
          </div>
        </div>
      </div>

      <div className="location-tip">
        <span>ⓘ</span>
        <span>點擊「使用目前位置」取得您的即時座標</span>
      </div>
    </section>
  );
}

export default LocationSelector;

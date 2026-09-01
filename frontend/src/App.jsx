import { useEffect, useState } from "react";
import LocationSelector from "./components/LocationSelector";
import WeatherCard from "./components/WeatherCard";
import ForecastCard from "./components/ForecastCard";
import { getWeather } from "./services/weatherService";
import "./App.css";

function App() {
  const [selectedCity, setSelectedCity] = useState("臺北市");

  const [location, setLocation] = useState({
    type: "city",
    city: "臺北市",
    latitude: null,
    longitude: null,
  });

  const [weather, setWeather] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadWeather() {
      try {
        setLoading(true);
        setError("");

        const data = await getWeather(location);

        setWeather(data);
      } catch (error) {
        console.error(error);
        setError("無法取得天氣資料");
      } finally {
        setLoading(false);
      }
    }

    loadWeather();
  }, [location]);

  return (
    <div className="app">
      <header className="app-header">
        <div>
          <h1>
            <span className="header-weather-icon">☀️</span>
            Weather App
          </h1>

          <p>臺灣天氣預報</p>
        </div>

        <div className="header-status">
          <span className="status-dot"></span>
          即時天氣資訊
        </div>
      </header>

      <main className="weather-dashboard">
        <LocationSelector
          selectedCity={selectedCity}
          setSelectedCity={setSelectedCity}
          location={location}
          setLocation={setLocation}
          weather={weather}
        />

        <WeatherCard weather={weather} loading={loading} error={error} />

        <ForecastCard weather={weather} loading={loading} error={error} />
      </main>

      <footer className="weather-footer">
        <span>☀️</span>
        <strong>貼心小提醒</strong>
        <span>天氣資訊僅供參考，外出請攜帶雨具並注意防曬。</span>
      </footer>
    </div>
  );
}

export default App;

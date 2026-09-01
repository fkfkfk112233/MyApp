import { useEffect, useState } from "react";
import LocationSelector from "./components/LocationSelector";
import WeatherCard from "./components/WeatherCard";
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

        const data = await getWeather(selectedCity);

        setWeather(data);
      } catch (error) {
        console.error(error);
        setError("無法取得天氣資料");
      } finally {
        setLoading(false);
      }
    }

    loadWeather();
  }, [selectedCity]);

  return (
    <div className="app">
      <h1>我的網站</h1>

      <LocationSelector
        selectedCity={selectedCity}
        setSelectedCity={setSelectedCity}
        location={location}
        setLocation={setLocation}
      />

      {loading && <p>載入中...</p>}

      {error && <p>{error}</p>}

      {!loading && !error && weather && <WeatherCard weather={weather} />}
    </div>
  );
}

export default App;

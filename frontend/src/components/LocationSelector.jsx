import { useState } from "react";

function LocationSelector({
  selectedCity,
  setSelectedCity,
  location,
  setLocation,
}) {
  function handleChange(event) {
    const city = event.target.value;

    setSelectedCity(city);

    setLocation({
      type: "city",
      city,
      latitude: null,
      longitude: null,
    });
  }

  const getCurrentLocation = () => {
    if (!navigator.geolocation) {
      console.log("瀏覽器不支援 Geolocation API");
      return;
    }

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
      },
      (error) => {
        console.error("取得 GPS 失敗:", error);
      },
    );
  };

  return (
    <div>
      <label htmlFor="city-select">選擇地點：</label>

      <select id="city-select" value={selectedCity} onChange={handleChange}>
        <option value="臺北市">臺北市</option>
        <option value="新北市">新北市</option>
        <option value="桃園市">桃園市</option>
        <option value="臺中市">臺中市</option>
        <option value="高雄市">高雄市</option>
      </select>

      <div>
        <button onClick={getCurrentLocation}>使用目前位置</button>

        <div>
          <p>地點來源：{location.type}</p>

          {location.type === "city" && <p>城市：{location.city}</p>}

          {location.type === "gps" && (
            <>
              <p>Latitude: {location.latitude}</p>
              <p>Longitude: {location.longitude}</p>
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default LocationSelector;

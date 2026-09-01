# Weather App - Frontend

Weather App 的 React 前端，負責 UI 顯示、地點選擇、GPS 定位，以及與 Spring Boot Backend API 溝通。

## Tech Stack

* React 19
* Vite
* JavaScript
* Fetch API
* Browser Geolocation API
* CSS
* ESLint

## Features

* 城市選擇
* GPS 定位
* 顯示 Latitude / Longitude
* 顯示目前位置
* 目前天氣卡片
* 未來天氣預報卡片
* 天氣圖示
* 溫度
* 降雨機率
* 預報時段
* Loading 狀態
* Error handling
* Responsive Dashboard UI

## Architecture

```text
React
 │
 ├── App.jsx
 │
 ├── LocationSelector
 │
 ├── WeatherCard
 │
 └── ForecastCard
 │
 ↓
weatherService.js
 │
 ↓
Spring Boot REST API
```

## Project Structure

```text
frontend/
│
├── README.md
├── package.json
├── index.html
├── eslint.config.js
│
└── src/
    │
    ├── App.jsx
    ├── App.css
    ├── index.css
    ├── main.jsx
    │
    ├── components/
    │   ├── LocationSelector.jsx
    │   ├── WeatherCard.jsx
    │   ├── WeatherCard.css
    │   └── ForecastCard.jsx
    │
    └── services/
        └── weatherService.js
```

## Component Responsibilities

### App.jsx

負責整個前端頁面的主要 State 與資料流。

目前主要 State：

```javascript
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
```

主要流程：

```text
location 改變
 ↓
useEffect
 ↓
getWeather(location)
 ↓
setWeather(data)
 ↓
重新 render
```

## Location Data Structure

目前地點資料：

### City

```javascript
{
  type: "city",
  city: "臺北市",
  latitude: null,
  longitude: null
}
```

### GPS

```javascript
{
  type: "gps",
  city: null,
  latitude: 25.044686,
  longitude: 121.516743
}
```

## LocationSelector

負責：

* 城市選擇
* GPS 定位
* 顯示定位錯誤
* 顯示目前位置來源
* 顯示 Latitude / Longitude

GPS 使用 Browser Geolocation API：

```javascript
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
  }
);
```

## Weather Service

`services/weatherService.js` 負責呼叫 Backend。

### City

```text
GET http://localhost:8080/api/weather?city=臺北市
```

### GPS

```text
GET http://localhost:8080/api/weather?latitude=25.044686&longitude=121.516743
```

Frontend 不直接呼叫 CWA。

```text
React
 ↓
Spring Boot
 ↓
CWA
```

這樣可以避免將 CWA API Key 暴露在瀏覽器端。

## Weather Data Structure

Backend 回傳：

```javascript
{
  current: {
    city: "臺北市",
    weather: "陰短暫陣雨或雷雨",
    weatherCode: "18",
    minTemperature: 28,
    maxTemperature: 31,
    rainProbability: 70,
    startTime: "2026-09-01 12:00:00",
    endTime: "2026-09-01 18:00:00"
  },

  forecasts: [
    {
      weather: "陰短暫陣雨或雷雨",
      weatherCode: "18",
      minTemperature: 28,
      maxTemperature: 31,
      rainProbability: 70,
      startTime: "2026-09-01 12:00:00",
      endTime: "2026-09-01 18:00:00"
    }
  ]
}
```

## WeatherCard

`WeatherCard` 顯示：

* 目前城市
* 預報時段
* 天氣圖示
* 天氣狀況
* 最高溫
* 最低溫
* 降雨機率

天氣圖示目前使用 CWA 提供的 SVG：

```javascript
const weatherIconUrl =
  `https://www.cwa.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/${current.weatherCode}.svg`;
```

## ForecastCard

`ForecastCard` 使用：

```javascript
weather.forecasts
```

顯示未來預報。

目前會排除第一個目前預報時段：

```javascript
const forecasts = (weather.forecasts || []).slice(1);
```

因此目前設計：

```text
WeatherCard
└── 目前時段

ForecastCard
├── 下一時段
└── 再下一時段
```

## UI Layout

目前採三欄 Dashboard：

```text
┌────────────────┬──────────────────┬──────────────────┐
│ Location       │ Current Weather  │ Forecast         │
│                │                  │                  │
│ 城市選擇        │ 目前天氣          │ 未來預報         │
│ GPS            │ 天氣圖示          │ 下一時段         │
│ Latitude       │ 溫度              │ 再下一時段       │
│ Longitude      │ 降雨機率          │                  │
└────────────────┴──────────────────┴──────────────────┘
```

## CSS Structure

```text
index.css
    ↓
全域 CSS

App.css
    ↓
Dashboard / Header / Footer

WeatherCard.css
    ↓
目前天氣卡片

ForecastCard
    ↓
未來預報卡片

LocationSelector
    ↓
地點卡片
```

目前 `index.css` 只保留全域基礎樣式，避免與元件 CSS 混在一起。

## Run

安裝套件：

```bash
npm install
```

啟動開發伺服器：

```bash
npm run dev
```

Build：

```bash
npm run build
```

Lint：

```bash
npm run lint
```

Preview：

```bash
npm run preview
```

## Development Server

預設：

```text
http://localhost:5173
```

Backend：

```text
http://localhost:8080
```

## Current Status

### Completed

* [x] React
* [x] Vite
* [x] App component
* [x] LocationSelector
* [x] WeatherCard
* [x] ForecastCard
* [x] Fetch API
* [x] Browser Geolocation API
* [x] City location
* [x] GPS location
* [x] Latitude / Longitude display
* [x] Loading state
* [x] Error handling
* [x] Current weather
* [x] Future forecast
* [x] CWA weather icons
* [x] Three-column UI
* [x] ESLint

### Planned

* [ ] 改善 Location state 結構
* [ ] 改善 Forecast 時段篩選
* [ ] 增加更完整的 API Error Message
* [ ] Frontend testing
* [ ] Responsive UI optimization
* [ ] 全球天氣支援

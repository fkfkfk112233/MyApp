# Weather App

一個使用 **React + Spring Boot** 開發的天氣預報網站。

目前以台灣地區天氣為主要目標，使用中央氣象署（CWA）提供的天氣預報資料，並支援手動選擇城市與瀏覽器 GPS 定位。

## Features

* 手動選擇台灣城市

  * 臺北市
  * 新北市
  * 桃園市
  * 臺中市
  * 高雄市
* 使用 Browser Geolocation API 取得目前位置
* GPS latitude / longitude 傳送至後端
* GPS 座標 Reverse Geocoding
* GPS 座標對應台灣城市
* 使用中央氣象署 CWA 取得天氣預報
* 顯示目前預報時段
* 顯示未來預報時段
* 顯示天氣圖示
* 顯示最高 / 最低溫度
* 顯示降雨機率
* Loading 狀態
* Error handling
* React ESLint
* 前後端分離架構
* API Key 使用環境變數管理

## Tech Stack

### Frontend

* React
* Vite
* JavaScript
* Browser Geolocation API
* Fetch API
* CSS

### Backend

* Java 21
* Spring Boot 4.1.1
* Spring Web MVC
* RestClient
* Maven

### External APIs

* Central Weather Administration (CWA)
* Geocoding API

## System Architecture

```text
┌──────────────────────┐
│      React           │
│                      │
│ LocationSelector     │
│ WeatherCard          │
│ ForecastCard         │
└──────────┬───────────┘
           │
           │ Fetch API
           ▼
┌──────────────────────┐
│ WeatherController    │
└──────────┬───────────┘
           ▼
┌──────────────────────┐
│ WeatherService       │
└───────┬────────┬─────┘
        │        │
        │        │ GPS
        │        ▼
        │   ┌────────────────┐
        │   │ GeocodeApiClient│
        │   └───────┬────────┘
        │           │
        │           ▼
        │      Geocoding API
        │           │
        │           ▼
        │          City
        │
        ▼
┌──────────────────────┐
│ WeatherApiClient     │
└──────────┬───────────┘
           │
           ▼
     CWA Weather API
           │
           ▼
┌──────────────────────┐
│ WeatherForecastResponse│
│                      │
│ current              │
│ forecasts            │
└──────────┬───────────┘
           │
           ▼
        React UI
```

## Request Flow

### 手動選擇城市

```text
User
 ↓
LocationSelector
 ↓
location.type = "city"
 ↓
weatherService.js
 ↓
GET /api/weather?city=臺北市
 ↓
WeatherController
 ↓
WeatherService
 ↓
WeatherApiClient
 ↓
CWA
 ↓
WeatherForecastResponse
 ↓
WeatherCard / ForecastCard
```

### GPS 定位

```text
User
 ↓
Browser Geolocation API
 ↓
latitude / longitude
 ↓
React location state
 ↓
weatherService.js
 ↓
GET /api/weather?latitude=25.x&longitude=121.x
 ↓
WeatherController
 ↓
WeatherService
 ↓
GeocodeApiClient
 ↓
Reverse Geocoding
 ↓
臺北市
 ↓
WeatherApiClient
 ↓
CWA
 ↓
WeatherForecastResponse
 ↓
WeatherCard / ForecastCard
```

## API Response

目前後端回傳：

```json
{
  "current": {
    "city": "臺北市",
    "weather": "陰短暫陣雨或雷雨",
    "weatherCode": "18",
    "minTemperature": 28,
    "maxTemperature": 31,
    "rainProbability": 70,
    "startTime": "2026-09-01 12:00:00",
    "endTime": "2026-09-01 18:00:00"
  },
  "forecasts": [
    {
      "weather": "陰短暫陣雨或雷雨",
      "weatherCode": "18",
      "minTemperature": 28,
      "maxTemperature": 31,
      "rainProbability": 70,
      "startTime": "2026-09-01 12:00:00",
      "endTime": "2026-09-01 18:00:00"
    }
  ]
}
```

`current` 用於目前天氣卡片。

`forecasts` 用於未來預報卡片。

## UI Layout

目前使用三欄式 Dashboard：

```text
┌────────────────┬──────────────────┬──────────────────┐
│                │                  │                  │
│  Location      │  Current Weather │  Forecast        │
│                │                  │                  │
│  選擇地點       │  目前天氣         │  未來預報         │
│                │                  │                  │
│  GPS           │  Weather Icon     │  Forecast 1      │
│  Latitude      │  Temperature      │  Forecast 2      │
│  Longitude     │  Rain Probability │                  │
│                │                  │                  │
└────────────────┴──────────────────┴──────────────────┘
```

### 左側

* 城市選擇
* GPS 定位
* GPS 座標
* 目前位置來源

### 中間

* 目前城市
* 目前預報時段
* 天氣圖示
* 天氣狀況
* 最高 / 最低溫
* 降雨機率

### 右側

* 未來預報
* 下一個預報時段
* 再下一個預報時段
* 天氣圖示
* 溫度
* 降雨機率

## Project Structure

```text
MyApp/
│
├── README.md
│
├── frontend/
│   ├── README.md
│   ├── package.json
│   ├── vite.config.js
│   └── src/
│       ├── App.jsx
│       ├── App.css
│       ├── index.css
│       ├── main.jsx
│       │
│       ├── components/
│       │   ├── LocationSelector.jsx
│       │   ├── WeatherCard.jsx
│       │   ├── WeatherCard.css
│       │   └── ForecastCard.jsx
│       │
│       └── services/
│           └── weatherService.js
│
└── backend/
    ├── README.md
    ├── pom.xml
    └── src/
        └── main/
            ├── java/backend/
            │   ├── BackendApplication.java
            │   ├── controller/
            │   ├── service/
            │   ├── client/
            │   ├── config/
            │   └── dto/
            │
            └── resources/
                └── application.properties
```

## Environment Variables

API Key 不放在 React 前端。

Backend 使用環境變數：

```text
CWA_API_KEY
GEOCODE_API_KEY
```

Spring Boot：

```properties
weather.api.key=${CWA_API_KEY}
geocode.api.key=${GEOCODE_API_KEY}
```

請勿將實際 API Key 寫入 Git。

## Run the Project

### 1. 啟動 Backend

```bash
cd backend
```

Windows：

```powershell
.\mvnw.cmd spring-boot:run
```

Backend 預設：

```text
http://localhost:8080
```

### 2. 啟動 Frontend

開啟另一個終端機：

```bash
cd frontend
npm install
npm run dev
```

Frontend 預設：

```text
http://localhost:5173
```

## API Endpoint

### City

```http
GET /api/weather?city=臺北市
```

### GPS

```http
GET /api/weather?latitude=25.044686&longitude=121.516743
```

## Future Global Weather Support

目前專案以台灣為主要目標。

GPS 本身可以取得全球座標，但目前的天氣資料來源是 CWA，因此流程目前為：

```text
GPS
 ↓
Reverse Geocoding
 ↓
Taiwan City
 ↓
CWA
```

未來若要支援全球，可以擴充為：

```text
GPS
 ↓
Reverse Geocoding
 ↓
Country
 ↓
Weather Provider
 ├── Taiwan → CWA
 └── Other Countries → Global Weather API
```

目前暫不實作，以避免過早增加系統複雜度。

## Disclaimer

天氣資訊僅供參考，實際天氣狀況可能與預報有所差異。

本專案主要作為 React、Spring Boot、REST API、GPS 與前後端整合練習使用。

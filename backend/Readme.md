# Weather App - Backend

Weather App 的 Spring Boot Backend。

主要負責：

* 接收 React Request
* 處理城市與 GPS 座標
* GPS Reverse Geocoding
* 呼叫中央氣象署 CWA
* 解析 CWA JSON
* 判斷目前預報時段
* 組合 Frontend 使用的 DTO
* 將 API Key 保留在 Backend

## Tech Stack

* Java 21
* Spring Boot 4.1.1
* Spring Web MVC
* RestClient
* Maven

## External APIs

### Central Weather Administration

使用 CWA：

```text
F-C0032-001
```

用於取得台灣地區 36 小時天氣預報資料。

### Geocoding

GPS 定位後，Backend 使用 Reverse Geocoding：

```text
latitude
longitude
 ↓
city
```

再使用城市名稱查詢 CWA。

## Architecture

```text
Controller
     ↓
  Service
     ↓
   Client
     ↓
External API
```

實際架構：

```text
                    ┌──────────────────┐
                    │ WeatherController│
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │  WeatherService  │
                    └───────┬───┬──────┘
                            │   │
                ┌───────────┘   └────────────┐
                ▼                            ▼
     ┌──────────────────┐          ┌──────────────────┐
     │ WeatherApiClient │          │ GeocodeApiClient │
     └────────┬─────────┘          └────────┬─────────┘
              │                             │
              ▼                             ▼
             CWA                     Reverse Geocoding
```

## Project Structure

```text
backend/
│
├── README.md
├── pom.xml
│
└── src/
    │
    ├── main/
    │   │
    │   ├── java/backend/
    │   │   │
    │   │   ├── BackendApplication.java
    │   │   │
    │   │   ├── controller/
    │   │   │   └── WeatherController.java
    │   │   │
    │   │   ├── service/
    │   │   │   └── WeatherService.java
    │   │   │
    │   │   ├── client/
    │   │   │   ├── WeatherApiClient.java
    │   │   │   └── GeocodeApiClient.java
    │   │   │
    │   │   ├── config/
    │   │   │   └── RestClientConfig.java
    │   │   │
    │   │   └── dto/
    │   │       ├── WeatherResponse.java
    │   │       ├── ForecastResponse.java
    │   │       ├── WeatherForecastResponse.java
    │   │       ├── GeocodeResponse.java
    │   │       └── cwa/
    │   │           └── CwaWeatherResponse.java
    │   │
    │   └── resources/
    │       └── application.properties
    │
    └── test/
        └── java/backend/
            └── BackendApplicationTests.java
```

## Controller

`WeatherController` 提供：

```http
GET /api/weather
```

### City

```http
GET /api/weather?city=臺北市
```

### GPS

```http
GET /api/weather?latitude=25.044686&longitude=121.516743
```

Controller 判斷 Request 使用哪一種地點來源：

```text
city
 ↓
WeatherService.getWeatherForecast(city)
```

或：

```text
latitude + longitude
 ↓
WeatherService.getWeatherForecast(latitude, longitude)
```

## WeatherService

`WeatherService` 是主要商業邏輯層。

目前負責：

1. 呼叫 CWA
2. 取得 Location
3. 找到目前預報時段
4. 取得 Wx
5. 取得 PoP
6. 取得 MinT
7. 取得 MaxT
8. 建立 `WeatherResponse`
9. 建立 `ForecastResponse`
10. 組合 `WeatherForecastResponse`

## Current Forecast Detection

CWA 回傳的預報不是單純的「現在天氣」。

因此 Backend 會根據：

```text
LocalDateTime.now()
```

與：

```text
startTime
endTime
```

判斷目前對應的預報時段。

條件：

```java
!now.isBefore(start) && now.isBefore(end)
```

也就是：

```text
start <= now < end
```

如果目前時間沒有落在任何時段，則會尋找下一個尚未開始的預報時段。

## Forecast Mapping

CWA 主要天氣資料：

```text
Wx
PoP
MinT
MaxT
```

Backend 會將相同預報時段的資料組合：

```text
Wx
 ├── weather
 └── weatherCode

PoP
 └── rainProbability

MinT
 └── minTemperature

MaxT
 └── maxTemperature
```

最後形成：

```text
ForecastResponse
```

## DTO

### WeatherResponse

代表目前天氣：

```text
city
weather
weatherCode
minTemperature
maxTemperature
rainProbability
startTime
endTime
```

### ForecastResponse

代表單一未來預報時段：

```text
weather
weatherCode
minTemperature
maxTemperature
rainProbability
startTime
endTime
```

### WeatherForecastResponse

最外層 API Response：

```text
current
forecasts
```

結構：

```text
WeatherForecastResponse
│
├── current
│     └── WeatherResponse
│
└── forecasts
      ├── ForecastResponse
      ├── ForecastResponse
      └── ForecastResponse
```

## GPS Flow

GPS Request：

```text
latitude
longitude
```

進入：

```text
WeatherController
 ↓
WeatherService
 ↓
GeocodeApiClient
 ↓
Reverse Geocoding
 ↓
city
 ↓
WeatherApiClient
 ↓
CWA
```

例如：

```text
25.044686
121.516743
```

反查：

```text
臺北市
```

然後：

```text
臺北市
 ↓
CWA
```

## WeatherApiClient

負責呼叫 CWA。

CWA Base URL：

```text
https://opendata.cwa.gov.tw
```

API：

```text
/api/v1/rest/datastore/F-C0032-001
```

Request：

```text
Authorization
locationName
```

## GeocodeApiClient

負責 Reverse Geocoding。

Request：

```text
lat
lon
api_key
```

Response 主要使用：

```text
address.city
```

目前台灣天氣流程以城市名稱作為 CWA 查詢依據。

## RestClient Configuration

目前有兩個 `RestClient`：

```java
@Bean
public RestClient cwaRestClient()
```

以及：

```java
@Bean
public RestClient geocodeRestClient()
```

透過不同的 Base URL 分離兩個外部 API。

Client 使用：

```java
@Qualifier("cwaRestClient")
```

或：

```java
@Qualifier("geocodeRestClient")
```

避免 Spring 不知道要注入哪一個 `RestClient`。

## Environment Variables

API Key 不直接寫在程式碼中。

`application.properties`：

```properties
spring.application.name=backend

weather.api.key=${CWA_API_KEY}

geocode.api.key=${GEOCODE_API_KEY}
```

需要設定：

```text
CWA_API_KEY
GEOCODE_API_KEY
```

不要將實際 Key 放入 Git repository。

## Run

進入 Backend：

```bash
cd backend
```

Windows：

```powershell
.\mvnw.cmd spring-boot:run
```

Backend：

```text
http://localhost:8080
```

## API

### City

```http
GET /api/weather?city=臺北市
```

### GPS

```http
GET /api/weather?latitude=25.044686&longitude=121.516743
```

### Example Response

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
    },
    {
      "weather": "陰短暫陣雨或雷雨",
      "weatherCode": "18",
      "minTemperature": 26,
      "maxTemperature": 28,
      "rainProbability": 70,
      "startTime": "2026-09-01 18:00:00",
      "endTime": "2026-09-02 06:00:00"
    },
    {
      "weather": "晴時多雲",
      "weatherCode": "2",
      "minTemperature": 26,
      "maxTemperature": 34,
      "rainProbability": 20,
      "startTime": "2026-09-02 06:00:00",
      "endTime": "2026-09-02 18:00:00"
    }
  ]
}
```

## Current Status

### Completed

* [x] Spring Boot project
* [x] REST Controller
* [x] Service layer
* [x] Client layer
* [x] RestClient
* [x] CWA API
* [x] CWA DTO
* [x] CWA JSON mapping
* [x] Current forecast period detection
* [x] Forecast mapping
* [x] WeatherForecastResponse
* [x] GPS coordinates
* [x] Reverse Geocoding
* [x] GPS → Taiwan city
* [x] Environment variable API Keys
* [x] Separate RestClient configuration
* [x] City API
* [x] GPS API

### Planned

* [ ] Refactor duplicated WeatherService logic
* [ ] Improve exception handling
* [ ] Add validation for latitude / longitude
* [ ] Add automated tests
* [ ] Improve forecast period filtering
* [ ] Global weather provider support
* [ ] Weather Provider abstraction

## Future Global Weather Support

目前 Backend 的流程：

```text
GPS
 ↓
Reverse Geocoding
 ↓
Taiwan City
 ↓
CWA
```

未來全球支援可以改成：

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

目前暫時維持台灣版本，避免過早增加 Provider abstraction 的複雜度。

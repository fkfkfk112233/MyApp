# My Project

React + Spring Boot 的天氣預報練習專案。

目前已完成 React 前端、Spring Boot 後端，以及中央氣象署（CWA）API 串接。

## 技術

- Frontend：React、Vite
- Backend：Spring Boot、RestClient
- API：中央氣象署 CWA
- API Key：Windows Environment Variable

## 目前功能

- 選擇縣市取得天氣資料
- 顯示天氣狀況
- 顯示天氣圖示
- 顯示最高 / 最低溫
- 顯示降雨機率
- 顯示目前對應的預報時段
- Loading / Error handling
- React → Spring Boot → CWA API 完整串接

## 專案架構

```text
MyApp/
├─ frontend/
│  └─ React
│
└─ backend/
   └─ Spring Boot
      ├─ controller/
      ├─ service/
      ├─ client/
      ├─ config/
      └─ dto/
```

## API 流程

```text
React
  ↓
Spring Boot Controller
  ↓
WeatherService
  ↓
WeatherApiClient
  ↓
CWA API
  ↓
WeatherResponse
  ↓
React WeatherCard
```

## 目前進度

- [x] React 天氣元件
- [x] Spring Boot API
- [x] CWA API 串接
- [x] DTO / Data Mapping
- [x] API Key 環境變數
- [x] 天氣圖示
- [x] 預報時段判斷
- [ ] GPS 定位
- [ ] 其他功能與 UI 優化

## 備註

目前先以開發練習為主，README 後續會隨專案完成度再補充完整。

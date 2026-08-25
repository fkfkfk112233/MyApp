export async function getWeather(city) {
  const response = await fetch(
    `http://localhost:8080/api/weather?city=${encodeURIComponent(city)}`
  );

  if (!response.ok) {
    throw new Error("API request failed");
  }

  return await response.json();
}
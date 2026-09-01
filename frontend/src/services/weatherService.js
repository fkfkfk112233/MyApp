export async function getWeather(location) {
  let url;

  if (location.type === "city") {
    url = `http://localhost:8080/api/weather?city=${encodeURIComponent(
      location.city,
    )}`;
  }

  if (location.type === "gps") {
    url = `http://localhost:8080/api/weather?latitude=${location.latitude}&longitude=${location.longitude}`;
  }

  if (!url) {
    throw new Error("Invalid location type");
  }

  const response = await fetch(url);

  if (!response.ok) {
    throw new Error("API request failed");
  }

  return await response.json();
}

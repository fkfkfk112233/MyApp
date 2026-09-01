export async function getWeather(location) {
  let url;

  if (location.type === "city") {
    url = `http://localhost:8080/api/weather?city=${encodeURIComponent(
      location.city,
    )}`;
  }

  const response = await fetch(url);

  if (!response.ok) {
    throw new Error("API request failed");
  }

  return await response.json();
}

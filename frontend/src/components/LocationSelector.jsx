function LocationSelector({ selectedCity, setSelectedCity }) {
  function handleChange(event) {
    setSelectedCity(event.target.value);
  }

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

      {/* <button>使用目前位置</button> */}
    </div>
  );
}

export default LocationSelector;

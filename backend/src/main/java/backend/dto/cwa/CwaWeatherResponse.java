package backend.dto.cwa;

import java.util.List;

public class CwaWeatherResponse {

    private Records records;

    public CwaWeatherResponse() {
    }

    public Records getRecords() {
        return records;
    }

    public void setRecords(Records records) {
        this.records = records;
    }

    public static class Records {

        private List<Location> location;

        public Records() {
        }

        public List<Location> getLocation() {
            return location;
        }

        public void setLocation(List<Location> location) {
            this.location = location;
        }
    }

    public static class Location {

        private String locationName;
        private List<WeatherElement> weatherElement;

        public Location() {
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public List<WeatherElement> getWeatherElement() {
            return weatherElement;
        }

        public void setWeatherElement(
                List<WeatherElement> weatherElement) {

            this.weatherElement = weatherElement;
        }
    }

    public static class WeatherElement {

        private String elementName;
        private List<Time> time;

        public WeatherElement() {
        }

        public String getElementName() {
            return elementName;
        }

        public void setElementName(String elementName) {
            this.elementName = elementName;
        }

        public List<Time> getTime() {
            return time;
        }

        public void setTime(List<Time> time) {
            this.time = time;
        }
    }

    public static class Time {

        private String startTime;
        private String endTime;
        private Parameter parameter;

        public Time() {
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Parameter getParameter() {
            return parameter;
        }

        public void setParameter(Parameter parameter) {
            this.parameter = parameter;
        }
    }

    public static class Parameter {

        private String parameterName;
        private String parameterValue;
        private String parameterUnit;

        public Parameter() {
        }

        public String getParameterName() {
            return parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getParameterValue() {
            return parameterValue;
        }

        public void setParameterValue(String parameterValue) {
            this.parameterValue = parameterValue;
        }

        public String getParameterUnit() {
            return parameterUnit;
        }

        public void setParameterUnit(String parameterUnit) {
            this.parameterUnit = parameterUnit;
        }
    }
}

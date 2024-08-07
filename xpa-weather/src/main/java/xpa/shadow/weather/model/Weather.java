package xpa.shadow.weather.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather {

    private Coordinates coordinates; // Coordinates
    private String condition; // Main condition (Rain, Snow, Clouds etc.)
    private String description; // Weather condition within the group
    private String icon; // Weather icon ID https://openweathermap.org/img/wn/10d@2x.png
    private float temperature; // Current temperature
    private float feelsLike; // This temperature parameter accounts for the human perception of weather
    private int pressure; // Atmospheric pressure at sea level. hPa
    private int pressureGroundLevel; // Atmospheric pressure at ground level. hPa
    private int humidity; // Current humidity. %
    private int cloudiness; // Cloudiness. %
    private float windSpeed; // Wind speed. m/s or mph
    private int windDirection; // Wind direction. deg
    private float windGust; // Wind gust. m/s or mph
    private int visibility; // Visibility in meters. Max value is 10 km
    private String countryCode; // Country code https://flagsapi.com/BE/flat/64.png
    private long sunrise; // Sunrise in UTC
    private long sunset; // Sunset in UTC
    private long datetime; // Time of data calculation in UTC
    private long timeShift; // Time shift from UTC
}

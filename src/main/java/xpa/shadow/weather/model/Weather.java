package xpa.shadow.weather.model;

import lombok.Builder;
import lombok.Data;
import xpa.shadow.weather.util.DateTimeUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Data
@Builder
public class Weather {

    private Coordinates coordinates; // Coordinates
    private String location; // Location
    private Units units; // Units
    private String condition; // Main condition (Rain, Snow, Clouds etc.)
    private String description; // Weather condition within the group
    private String icon; // Weather icon ID https://openweathermap.org/img/wn/10d@2x.png
    private float temperature; // Current temperature. ºC or ºF
    private float feelsLike; // This temperature parameter accounts for the human perception of weather. ºC or ºF
    private int pressureSeaLevel; // Atmospheric pressure at sea level. hPa
    private int pressureGroundLevel; // Atmospheric pressure at ground level. hPa
    private int humidity; // Current humidity. %
    private int cloudiness; // Cloudiness. %
    private float windSpeed; // Wind speed. m/s or mph
    private int windDirection; // Wind direction. deg
    private int visibility; // Visibility in meters. Max value is 10 km
    private String countryCode; // Country code https://flagsapi.com/BE/flat/64.png
    private long sunrise; // Sunrise in UTC
    private long sunset; // Sunset in UTC
    private long datetime; // Time of data calculation in UTC
    private long timezone; // Time shift from UTC
    private List<Forecast> forecastList;

    public String getLocalDayOfWeek(Locale locale) {
        return LocalDateTime.ofEpochSecond(datetime + timezone, 0, ZoneOffset.UTC)
                .getDayOfWeek()
                .getDisplayName(TextStyle.FULL, locale);
    }

    public String getLocalDateTime(Locale locale) {
        return DateTimeUtils.DATE_TIME_FORMATTER_SHORT
                .localizedBy(locale)
                .format(LocalDateTime.ofEpochSecond(datetime + timezone, 0, ZoneOffset.UTC));
    }

    public String getLocalSunrise(Locale locale) {
        return DateTimeUtils.DATE_TIME_FORMATTER_SHORT
                .localizedBy(locale)
                .format(LocalDateTime.ofEpochSecond(sunrise + timezone, 0, ZoneOffset.UTC));
    }

    public String getLocalSunset(Locale locale) {
        return DateTimeUtils.DATE_TIME_FORMATTER_SHORT
                .localizedBy(locale)
                .format(LocalDateTime.ofEpochSecond(sunset + timezone, 0, ZoneOffset.UTC));
    }
}

package xpa.shadow.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * OWM = OpenWeatherMap
 */
public class OWMForecastResponse {

    private List<Forecast> list;
    private City city;

    public List<xpa.shadow.weather.model.Forecast> toForecastList() {
        List<xpa.shadow.weather.model.Forecast> forecastList = new ArrayList<>(list.size());

        for (Forecast forecast : list) {
            Forecast.Weather forecastWeather = forecast.weather.get(0);

            forecastList.add(xpa.shadow.weather.model.Forecast.builder()
                    .condition(forecastWeather.main)
                    .description(forecastWeather.description)
                    .icon(forecastWeather.icon)
                    .temperature(forecast.main.temp)
                    .feelsLike(forecast.main.feelsLike)
                    .pressureSeaLevel(forecast.main.seaLevel)
                    .pressureGroundLevel(forecast.main.grndLevel)
                    .humidity(forecast.main.humidity)
                    .cloudiness(forecast.clouds.all)
                    .windSpeed(forecast.wind.speed)
                    .windDirection(forecast.wind.deg)
                    .visibility(forecast.visibility)
                    .precipitation((int) forecast.pop * 100)
                    .datetime(forecast.dt)
                    .timezone(city.timezone)
                    .build());
        }

        return forecastList;
    }

    public static class Forecast {
        private long dt;
        private Main main;
        private List<Weather> weather;
        private Cloud clouds;
        private Wind wind;
        private int visibility;
        private float pop;

        public static class Main {
            private float temp;
            @SerializedName("feels_like")
            private float feelsLike;
            @SerializedName("sea_level")
            private int seaLevel;
            @SerializedName("grnd_level")
            private int grndLevel;
            private int humidity;
        }

        public static class Weather {
            private String main;
            private String description;
            private String icon;
        }

        public static class Cloud {
            private int all;
        }

        public static class Wind {
            private float speed;
            private int deg;
        }
    }

    public static class City {
        private long timezone;
    }
}

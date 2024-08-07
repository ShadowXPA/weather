package xpa.shadow.weather.service;

import org.springframework.stereotype.Service;
import xpa.shadow.weather.model.Coordinates;
import xpa.shadow.weather.model.Weather;

import java.util.Optional;

@Service
public interface WeatherApiService {

    Optional<Weather> getWeather(String apiKey, Coordinates coordinates);
}

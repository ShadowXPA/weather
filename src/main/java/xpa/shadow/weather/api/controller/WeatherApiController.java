package xpa.shadow.weather.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xpa.shadow.weather.api.error.ApiError;
import xpa.shadow.weather.model.Language;
import xpa.shadow.weather.model.Units;
import xpa.shadow.weather.model.Weather;
import xpa.shadow.weather.service.WeatherService;

import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/api/v1/weather")
public class WeatherApiController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<Object> getWeather(HttpServletRequest request,
                                             @RequestParam("q") String location,
                                             @RequestParam(value = "units", required = false, defaultValue = "metric") Units units,
                                             @RequestParam("geo-api-key") String geocodingApiKey,
                                             @RequestParam("weather-api-key") String weatherApiKey,
                                             @RequestParam(value = "lang", required = false) String lang) {
        log.info("Getting weather information...");

        if (location.isBlank()) {
            log.warn("Address is empty!");
            ApiError error = new ApiError();
            error.setHttpStatus(HttpStatus.BAD_REQUEST);
            error.setMessage("Empty location");
            error.setPath(request.getRequestURI());
            return ResponseEntity.badRequest().body(error);
        }

        if (geocodingApiKey.isBlank()) {
            log.warn("Geocoding API key is empty!");
            ApiError error = new ApiError();
            error.setHttpStatus(HttpStatus.BAD_REQUEST);
            error.setMessage("Empty Geocoding API key");
            error.setPath(request.getRequestURI());
            return ResponseEntity.badRequest().body(error);
        }

        if (weatherApiKey.isBlank()) {
            log.warn("Weather API key is empty!");
            ApiError error = new ApiError();
            error.setHttpStatus(HttpStatus.BAD_REQUEST);
            error.setMessage("Empty Weather API key");
            error.setPath(request.getRequestURI());
            return ResponseEntity.badRequest().body(error);
        }

        Language language = (lang == null || lang.isBlank())
                ? Language.getByLocale(request.getLocale())
                : Language.getByLang(lang);

        Optional<Weather> weather =
                weatherService.getWeather(geocodingApiKey, weatherApiKey, location, units, language);

        if (weather.isEmpty()) {
            log.warn("No weather information!");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(weather.get());
    }
}

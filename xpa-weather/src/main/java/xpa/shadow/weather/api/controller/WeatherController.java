package xpa.shadow.weather.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xpa.shadow.weather.api.error.ApiError;
import xpa.shadow.weather.model.Unit;
import xpa.shadow.weather.service.GeocodeApiService;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    @Autowired
    private GeocodeApiService geocodeService;

    @GetMapping
    public ResponseEntity<Object> getWeather(HttpServletRequest request,
                                             @RequestParam("q") String query,
                                             @RequestParam(value = "units", required = false, defaultValue = "metric") Unit units,
                                             @RequestParam("geo-api-key") String geocodingApiKey,
                                             @RequestParam("weather-api-key") String weatherApiKey) {
        if (query.isBlank()) {
            ApiError error = new ApiError();
            error.setHttpStatus(HttpStatus.BAD_REQUEST);
            error.setMessage("Empty query");
            error.setPath(request.getRequestURI());
            return ResponseEntity.badRequest().body(error);
        }

        if (geocodingApiKey.isBlank()) {
            ApiError error = new ApiError();
            error.setHttpStatus(HttpStatus.BAD_REQUEST);
            error.setMessage("Empty Geocode API key");
            error.setPath(request.getRequestURI());
            return ResponseEntity.badRequest().body(error);
        }

        if (weatherApiKey.isBlank()) {
            ApiError error = new ApiError();
            error.setHttpStatus(HttpStatus.BAD_REQUEST);
            error.setMessage("Empty OpenWeatherMap API key");
            error.setPath(request.getRequestURI());
            return ResponseEntity.badRequest().body(error);
        }

        return ResponseEntity.ok(geocodeService.getCoordinates(geocodingApiKey, query));
    }
}

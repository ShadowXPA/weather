package xpa.shadow.weather.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xpa.shadow.weather.model.Language;
import xpa.shadow.weather.model.Units;
import xpa.shadow.weather.model.Weather;
import xpa.shadow.weather.service.WeatherService;

import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public String get(Model model) {
        return "weather/index";
    }

    @PostMapping
    public String getWeather(HttpServletRequest request,
                             @RequestParam("location") String location,
                             @RequestParam(value = "units", required = false, defaultValue = "metric") Units units,
                             @RequestParam("weather-api-key") String weatherApiKey,
                             @RequestParam("geocoding-api-key") String geocodingApiKey,
                             RedirectAttributes redirectAttributes) {
        Optional<Weather> weather = weatherService.getWeather(geocodingApiKey, weatherApiKey, location, units,
                Language.getByLocale(request.getLocale()));
        redirectAttributes.addFlashAttribute("location", location);
        redirectAttributes.addFlashAttribute("units", units);
        redirectAttributes.addFlashAttribute("isMetric", units == Units.METRIC);
        redirectAttributes.addFlashAttribute("weatherApiKey", weatherApiKey);
        redirectAttributes.addFlashAttribute("geocodingApiKey", geocodingApiKey);
        weather.ifPresent(value -> redirectAttributes.addFlashAttribute("weather", value));
        return "redirect:/";
    }
}

package xpa.shadow.weather.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xpa.shadow.weather.model.Coordinates;
import xpa.shadow.weather.model.Weather;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Weather service from <a href="https://openweathermap.org/">OpenWeatherMap</a>
 */
@Log4j2
@Service("OpenWeatherMap")
public class OpenWeatherMapApiService implements WeatherApiService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Override
    public Optional<Weather> getWeather(String apiKey, Coordinates coordinates) {
        log.info("Getting weather for coordinates: {}...", coordinates);
        log.debug("Using OpenWeatherMap API Key: {}", apiKey);

        HttpGet weatherRequest = new HttpGet("https://api.openweathermap.org/data/2.5/weather?"
                + "appid=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
                + "&lat=" + coordinates.getLatitude()
                + "&lon=" + coordinates.getLongitude());

        HttpGet forecastRequest = new HttpGet("api.openweathermap.org/data/2.5/forecast?"
                + "appid=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
                + "&lat=" + coordinates.getLatitude()
                + "&lon=" + coordinates.getLongitude());

        try (CloseableHttpResponse response = httpClient.execute(weatherRequest)) {
            HttpStatus httpStatus = HttpStatus.resolve(response.getStatusLine().getStatusCode());
            log.info("Got response code: {}", httpStatus);
            if (httpStatus == HttpStatus.OK) {
                String responseString = EntityUtils.toString(response.getEntity());
                JsonArray jsonArray = JsonParser.parseString(responseString).getAsJsonArray();
                JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error("An error occurred while trying to call the OpenWeatherMap API", ex);
        }

        return Optional.empty();
    }
}

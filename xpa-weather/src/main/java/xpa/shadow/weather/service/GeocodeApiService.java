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
import xpa.shadow.weather.model.Address;
import xpa.shadow.weather.model.Coordinates;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Geocoding service from <a href="https://geocode.maps.co/">Geocode</a>
 */
@Log4j2
@Service("Geocode")
public class GeocodeApiService implements GeocodingApiService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Override
    public Optional<Coordinates> getCoordinates(String apiKey, String location) {
        log.info("Getting coordinates for location: '{}'...", location);
        log.debug("Using API Key: '{}'", apiKey);

        HttpGet request = new HttpGet("https://geocode.maps.co/search?"
                + "api_key=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
                + "&q=" + URLEncoder.encode(location, StandardCharsets.UTF_8));

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpStatus httpStatus = HttpStatus.resolve(response.getStatusLine().getStatusCode());
            log.info("Got response code: {}", httpStatus);
            if (httpStatus == HttpStatus.OK) {
                String responseString = EntityUtils.toString(response.getEntity());
                JsonArray root = JsonParser.parseString(responseString).getAsJsonArray();
                JsonObject jsonObject = root.get(0).getAsJsonObject();
                return Optional.of(new Coordinates(Double.parseDouble(jsonObject.get("lat").getAsString()),
                        Double.parseDouble(jsonObject.get("lon").getAsString()),
                        jsonObject.get("display_name").getAsString()));
            }
        } catch (Exception ex) {
            log.error("An error occurred while trying to call the API", ex);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Address> getAddress(String apiKey, float latitude, float longitude) {
        log.info("Getting address from coordinates: Latitude: {}, Longitude: {}...", latitude, longitude);
        log.debug("Using API Key: '{}'", apiKey);

        HttpGet request = new HttpGet("https://geocode.maps.co/reverse?"
                + "api_key=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
                + "&lat=" + latitude
                + "&lon=" + longitude);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpStatus httpStatus = HttpStatus.resolve(response.getStatusLine().getStatusCode());
            log.info("Got response code: {}", httpStatus);
            if (httpStatus == HttpStatus.OK) {
                String responseString = EntityUtils.toString(response.getEntity());
                JsonObject root = JsonParser.parseString(responseString).getAsJsonObject();
                String address = root.get("display_name").getAsString();
                return Optional.of(new Address(address));
            }
        } catch (Exception ex) {
            log.error("An error occurred while trying to call the API", ex);
        }

        return Optional.empty();
    }
}

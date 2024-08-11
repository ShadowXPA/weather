package xpa.shadow.weather.service;

import org.springframework.stereotype.Service;
import xpa.shadow.weather.model.Address;
import xpa.shadow.weather.model.Coordinates;

import java.util.Optional;

@Service
public interface GeocodingApiService {

    Optional<Coordinates> getCoordinates(String apiKey, String location);

    Optional<Address> getAddress(String apiKey, float latitude, float longitude);
}

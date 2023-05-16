package fer.seminar2.core;

import fer.seminar2.core.model.HourlyTemperature;
import fer.seminar2.core.model.TimePeriod;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class Collector {

    private static final String CITY = "Zagreb";
    private static final int DAYS = 2;
    private static final String URL = "http://api.weatherapi.com/v1/forecast.json";
    @Value("${weather-api-key}")
    private String apiKey;
    @NonNull
    private RestTemplate restTemplate;

    public JSONObject fetchWeatherData() {
        return null;
    }

    public List<HourlyTemperature> getWeatherPredictionValuesForNext24h() {
        return null;
    }

    public LocalDateTime calculateStartTime() {
        return null;
    }

    public List<HourlyTemperature> loadDataIntoList(JSONObject weatherDataJson) {
        return null;
    }

    public TimePeriod calculateTimePeriod() {
        return null;
    }

}

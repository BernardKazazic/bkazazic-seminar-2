package fer.seminar2.core;

import fer.seminar2.core.model.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Collector {
    private static final String CITY = "Zagreb";
    private static final int DAYS = 2;
    private static final String URL = "http://api.weatherapi.com/v1/forecast.json";
    @Value("${weather-api-key}")
    private String apiKey;
    @NonNull
    private RestTemplate restTemplate;
    @NonNull
    private DateTimeFormatter dateTimeFormatter;

    public List<HourlyTemperature> getWeatherPredictionValuesForNext24h() {
        Forecast forecast24h = fetchWeatherData();
        TimePeriod timePeriod = calculateTimePeriod();
        return mapAndFilterHourlyForecast(forecast24h.forecastday(), timePeriod);
    }

    private Forecast fetchWeatherData() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("key", apiKey)
                .queryParam("q", CITY)
                .queryParam("days", DAYS);

        ResponseEntity<WeatherData> response = restTemplate.getForEntity(uriBuilder.toUriString(), WeatherData.class);

        return response.getBody().forecast();
    }

    private TimePeriod calculateTimePeriod() {
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        startTime.format(dateTimeFormatter);
        LocalDateTime endTime = startTime.plusHours(24);
        return new TimePeriod(startTime, endTime);
    }

    private List<HourlyTemperature> mapAndFilterHourlyForecast(List<ForecastDay> forecastDays, TimePeriod timePeriod) {
        return forecastDays.stream()
                .flatMap(forecastDay -> forecastDay.hour().stream())
                .map(hourlyForecast -> new HourlyTemperature(LocalDateTime.parse(hourlyForecast.time(), dateTimeFormatter), hourlyForecast.temp_c()))
                .filter(hourlyTemperature ->
                        (hourlyTemperature.dateTime().isEqual(timePeriod.startTime()) || hourlyTemperature.dateTime().isAfter(timePeriod.startTime())) &&
                                (hourlyTemperature.dateTime().isEqual(timePeriod.endTime()) || hourlyTemperature.dateTime().isBefore(timePeriod.endTime())))
                .collect(Collectors.toList());
    }

}

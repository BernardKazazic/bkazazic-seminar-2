package fer.seminar2.core;

import fer.seminar2.core.model.Forecast;
import fer.seminar2.core.model.ForecastDay;
import fer.seminar2.core.model.HourlyTemperature;
import fer.seminar2.core.model.TimePeriod;
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

    public Forecast fetchWeatherData() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("key", apiKey)
                .queryParam("q", CITY)
                .queryParam("days", DAYS);

        ResponseEntity<Forecast> response = restTemplate.getForEntity(uriBuilder.toUriString(), Forecast.class);

        return response.getBody();
    }

    public List<HourlyTemperature> getWeatherPredictionValuesForNext24h() {
        Forecast forecast24h = fetchWeatherData();
        TimePeriod timePeriod = calculateTimePeriod();
        List<HourlyTemperature> hourlyTemperatures = mapAndFilterHourlyForecast(forecast24h.forecastDays(), timePeriod);
        return hourlyTemperatures;
    }

    public List<HourlyTemperature> mapAndFilterHourlyForecast(List<ForecastDay> forecastDays, TimePeriod timePeriod) {
        return forecastDays.stream()
                .flatMap(forecastDay -> forecastDay.hourlyForecast().stream())
                .map(hourlyForecast -> new HourlyTemperature(LocalDateTime.parse(hourlyForecast.time(), dateTimeFormatter), hourlyForecast.tempC()))
                .filter(hourlyTemperature ->
                        (hourlyTemperature.dateTime().isEqual(timePeriod.startTime()) || hourlyTemperature.dateTime().isAfter(timePeriod.startTime())) &&
                                (hourlyTemperature.dateTime().isEqual(timePeriod.endTime()) || hourlyTemperature.dateTime().isBefore(timePeriod.endTime())))
                .collect(Collectors.toList());
    }

    public TimePeriod calculateTimePeriod() {
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        startTime.format(dateTimeFormatter);
        LocalDateTime endTime = startTime.plusHours(24);
        return new TimePeriod(startTime, endTime);
    }

}

package fer.seminar2.infrastructure.service;

import fer.seminar2.application.WeatherDataCollector;
import fer.seminar2.interfaces.dto.controller.HourlyTemperatures24hDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {
    @NonNull
    private WeatherDataCollector weatherDataCollector;

    public HourlyTemperatures24hDto getHourlyTemperatures() {
        return new HourlyTemperatures24hDto(weatherDataCollector.getWeatherPredictionValuesForNext24h());
    }
}

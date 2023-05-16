package fer.seminar2.api.service;

import fer.seminar2.api.dto.HourlyTemperatureDTO;
import fer.seminar2.core.Collector;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestService {
    @NonNull
    private Collector collector;

    public HourlyTemperatureDTO getHourlyTemperatures() {
        return new HourlyTemperatureDTO(collector.getWeatherPredictionValuesForNext24h());
    }
}

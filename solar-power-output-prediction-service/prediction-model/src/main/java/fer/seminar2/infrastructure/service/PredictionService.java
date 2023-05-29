package fer.seminar2.infrastructure.service;

import fer.seminar2.application.PredictionModel;
import fer.seminar2.interfaces.dto.HourlyTemperatureList;
import fer.seminar2.interfaces.mapper.HourlyTemperatureMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class PredictionService {
    @NonNull
    private PredictionModel predictionModel;
    @NonNull
    private RestTemplate restTemplate;
    @NonNull
    private String weatherDataCollectorUrl;

    public void getPrediction() {
        ResponseEntity<HourlyTemperatureList> response = restTemplate.exchange(URI.create(weatherDataCollectorUrl), HttpMethod.GET, null, HourlyTemperatureList.class);
        HourlyTemperatureList hourlyTemperatureList = response.getBody();
        double[][] data = HourlyTemperatureMapper.toArray(hourlyTemperatureList);
        predictionModel.predictPowerOutput24h(data);
    }
}

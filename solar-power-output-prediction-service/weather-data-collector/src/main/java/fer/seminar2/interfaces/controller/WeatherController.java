package fer.seminar2.interfaces.controller;

import fer.seminar2.infrastructure.service.WeatherService;
import fer.seminar2.interfaces.dto.controller.HourlyTemperatures24hDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    @NonNull
    private WeatherService weatherService;

    @GetMapping("/get-hourly-temperatures")
    public ResponseEntity<HourlyTemperatures24hDto> getHourlyTemperatures() {
        return ResponseEntity.ok().body(weatherService.getHourlyTemperatures());
    }
}

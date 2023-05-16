package fer.seminar2.api.controller;

import fer.seminar2.api.dto.HourlyTemperatureDTO;
import fer.seminar2.api.service.RestService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RestController {
    @NonNull
    private RestService restService;

    @GetMapping("/get-hourly-temperatures")
    public ResponseEntity<HourlyTemperatureDTO> getHourlyTemperatures() {
        return ResponseEntity.ok().body(restService.getHourlyTemperatures());
    }
}

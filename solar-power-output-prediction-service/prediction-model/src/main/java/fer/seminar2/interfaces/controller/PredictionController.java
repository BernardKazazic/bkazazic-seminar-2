package fer.seminar2.interfaces.controller;

import fer.seminar2.infrastructure.service.PredictionService;
import fer.seminar2.interfaces.dto.PredictionDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PredictionController {
    @NonNull
    private PredictionService predictionService;

    @GetMapping("/prediction")
    public ResponseEntity<PredictionDto> getPrediction() {
        return ResponseEntity.ok(null);
    }
}

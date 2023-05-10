package fer.seminar2.controller;

import fer.seminar2.model.ChartData;
import fer.seminar2.service.PredictionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestController {
    private PredictionService predictionService;

    @GetMapping("/getGraphData")
    public ResponseEntity<ChartData> getChartData() {
        return ResponseEntity.ok().body(predictionService.getChartData());
    }
}

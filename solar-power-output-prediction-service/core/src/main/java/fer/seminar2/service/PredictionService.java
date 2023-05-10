package fer.seminar2.service;

import fer.seminar2.model.ChartData;
import fer.seminar2.simulation.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PredictionService {
    private SimulationService simulationService;

    public ChartData getChartData() {
        return null;
    }
}

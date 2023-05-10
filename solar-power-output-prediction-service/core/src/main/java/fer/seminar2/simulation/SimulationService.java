package fer.seminar2.simulation;

import fer.seminar2.model.LivePowerOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimulationService {

    private Long startTime;

    public LivePowerOutput readLivePowerOutput() {
        return null;
    }
}

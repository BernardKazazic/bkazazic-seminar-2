package fer.seminar2.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ChartData {
    private List<Date> dateTimes;
    private List<Double> energyOutputs;
    private List<Double> temperatures;
}

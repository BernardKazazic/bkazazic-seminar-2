package fer.seminar2.api.service;

import fer.seminar2.core.DataCollector;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.wimpi.modbus.ModbusException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestService {
    @NonNull
    private DataCollector dataCollector;

    public Float getCurrentPower() {
        try {
            return dataCollector.getCurrentACPower();
        } catch (ModbusException e) {
            e.printStackTrace();
            return -1.0f;
        }
    }
}

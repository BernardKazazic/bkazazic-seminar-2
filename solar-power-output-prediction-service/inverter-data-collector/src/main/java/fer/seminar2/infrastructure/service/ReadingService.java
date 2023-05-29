package fer.seminar2.infrastructure.service;

import fer.seminar2.application.ReadingCollector;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.wimpi.modbus.ModbusException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadingService {
    @NonNull
    private ReadingCollector readingCollector;

    public Float getPowerReading() {
        try {
            return readingCollector.getCurrentACPower();
        } catch (ModbusException e) {
            e.printStackTrace();
            return -1.0f;
        }
    }
}

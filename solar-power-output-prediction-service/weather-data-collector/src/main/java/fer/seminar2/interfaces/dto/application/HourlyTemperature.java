package fer.seminar2.interfaces.dto.application;

import java.time.LocalDateTime;

public record HourlyTemperature(LocalDateTime dateTime, Double temperature) {
}

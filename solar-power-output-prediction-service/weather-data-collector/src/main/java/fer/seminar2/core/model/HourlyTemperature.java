package fer.seminar2.core.model;

import java.time.LocalDateTime;

public record HourlyTemperature(LocalDateTime dateTime, Double temperature) {
}

package fer.seminar2.interfaces.dto;

import java.time.LocalDateTime;

public record HourlyTemperatureDto(LocalDateTime dateTime, Double temperature) {
}

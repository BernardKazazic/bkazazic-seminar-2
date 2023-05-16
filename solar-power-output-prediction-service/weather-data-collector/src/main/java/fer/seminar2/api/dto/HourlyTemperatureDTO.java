package fer.seminar2.api.dto;

import fer.seminar2.core.model.HourlyTemperature;

import java.util.List;

public record HourlyTemperatureDTO(List<HourlyTemperature> hourlyTemperatures) {
}

package fer.seminar2.interfaces.dto.controller;

import fer.seminar2.interfaces.dto.application.HourlyTemperature;

import java.util.List;

public record HourlyTemperatures24hDto(List<HourlyTemperature> hourlyTemperatures) {
}

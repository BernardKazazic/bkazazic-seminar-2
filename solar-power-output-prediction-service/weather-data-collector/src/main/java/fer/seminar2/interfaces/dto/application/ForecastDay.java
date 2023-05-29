package fer.seminar2.interfaces.dto.application;

import java.util.List;

public record ForecastDay(List<HourlyForecast> hour) {
}

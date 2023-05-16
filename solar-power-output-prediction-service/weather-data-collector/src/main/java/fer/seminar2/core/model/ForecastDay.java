package fer.seminar2.core.model;

import java.util.List;

public record ForecastDay(List<HourlyForecast> hourlyForecast) {
}

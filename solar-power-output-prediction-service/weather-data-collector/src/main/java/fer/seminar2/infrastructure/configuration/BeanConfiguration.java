package fer.seminar2.infrastructure.configuration;

import fer.seminar2.application.WeatherDataCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;

@Configuration
public class BeanConfiguration {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    @Bean
    public WeatherDataCollector getCollector() {
        return new WeatherDataCollector(getRestTemplate(), getDateTimeFormatter());
    }
}

package fer.seminar2.configuration;

import fer.seminar2.core.DataCollector;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    @NonNull
    private InverterConfiguration inverterConfiguration;

    @Bean
    public DataCollector getDataCollector() {
        return new DataCollector(inverterConfiguration);
    }
}

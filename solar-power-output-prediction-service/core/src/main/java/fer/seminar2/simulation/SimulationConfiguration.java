package fer.seminar2.simulation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimulationConfiguration {

    @Bean(name = "startTime")
    public Long startTime() {
        return System.currentTimeMillis();
    }
}

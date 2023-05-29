package fer.seminar2;

import fer.seminar2.configuration.InverterConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(InverterConfiguration.class)
public class DataCollectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataCollectorApplication.class, args);
    }
}

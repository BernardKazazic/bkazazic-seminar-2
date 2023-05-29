package fer.seminar2;

import fer.seminar2.infrastructure.configuration.InverterConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(InverterConfiguration.class)
public class InverterApplication {
    public static void main(String[] args) {
        SpringApplication.run(InverterApplication.class, args);
    }
}

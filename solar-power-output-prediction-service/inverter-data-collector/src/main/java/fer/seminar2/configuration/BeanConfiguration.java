package fer.seminar2.configuration;

import fer.seminar2.core.DataCollector;
import fer.seminar2.domain.repository.ReadingRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    @NonNull
    private InverterConfiguration inverterConfiguration;
    @NonNull
    private DSLContext dslContext;

    @Bean
    public DataCollector getDataCollector() {
        return new DataCollector(inverterConfiguration);
    }

    @Bean
    public ReadingRepository getReadingRepository() {
        return new ReadingRepository(dslContext);
    }

}

package fer.seminar2.infrastructure.service;

import fer.seminar2.application.ReadingCollector;
import fer.seminar2.domain.repository.ReadingRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersistenceService {
    @NonNull
    private ReadingCollector readingCollector;
    @NonNull
    private ReadingRepository readingRepository;

    @Scheduled(cron = "0 * * * * *")
    public void saveReadings() {
        try {
            Double activePower = (double) readingCollector.getCurrentACPower();
            readingRepository.createReading(activePower);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

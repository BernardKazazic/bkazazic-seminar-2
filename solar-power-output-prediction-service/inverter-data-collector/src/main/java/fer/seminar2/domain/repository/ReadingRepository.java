package fer.seminar2.domain.repository;

import fer.seminar2.domain.model.Reading;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import java.math.BigDecimal;

import static fer.seminar2.Tables.READING;


@RequiredArgsConstructor
public class ReadingRepository {
    @NonNull
    private DSLContext dslContext;

    public Reading createReading(Double activePower) {
        try {
            return dslContext.insertInto(READING).columns(READING.ACTIVE_POWER).values(BigDecimal.valueOf(activePower)).returning().fetchSingleInto(Reading.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

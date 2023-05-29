package fer.seminar2.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Reading {
    private Integer id;
    private LocalDateTime createdAt;
    private Double activePower;

    public Reading(Double activePower) {
        this.activePower = activePower;
    }
}

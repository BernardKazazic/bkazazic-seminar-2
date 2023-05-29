package fer.seminar2.interfaces.controller;

import fer.seminar2.infrastructure.service.ReadingService;
import fer.seminar2.interfaces.dto.PowerReadingDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ReadingController {
    @NonNull
    private ReadingService service;

    @GetMapping("/reading/power")
    public ResponseEntity<PowerReadingDto> getPowerReading() {
        PowerReadingDto powerReadingDTO = new PowerReadingDto(service.getPowerReading());
        return ResponseEntity.ok().body(powerReadingDTO);
    }
}

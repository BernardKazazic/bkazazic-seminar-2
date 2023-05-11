package fer.seminar2.api.controller;

import fer.seminar2.api.dto.CurrentPowerDTO;
import fer.seminar2.api.service.RestService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RestController {
    @NonNull
    private RestService service;

    @GetMapping("/getCurrentPower")
    public ResponseEntity<CurrentPowerDTO> getCurrentPower() {
        CurrentPowerDTO currentPowerDTO = new CurrentPowerDTO(service.getCurrentPower());
        return ResponseEntity.ok().body(currentPowerDTO);
    }
}

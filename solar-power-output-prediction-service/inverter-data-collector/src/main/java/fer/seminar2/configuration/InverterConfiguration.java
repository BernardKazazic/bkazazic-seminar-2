package fer.seminar2.configuration;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "inverter")
public class InverterConfiguration {
    @NonNull
    private List<String> address = new ArrayList<>();
    @NonNull
    private Integer port;
    @NonNull
    private Integer startRegister;
    @NonNull
    private Integer registerCount;
}

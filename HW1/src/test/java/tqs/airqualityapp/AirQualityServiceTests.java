package tqs.airqualityapp;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isNull;

@ExtendWith(MockitoExtension.class)
class AirQualityServiceTests {

    @InjectMocks
    @Spy
    private AirQualityService airQualityService;

    private static LocalDate date = LocalDate.of(2023, 3, 24);

    @BeforeEach
    public void setUp() throws Exception {
        String parisUrlWithParameters = "https://api.weatherapi.com/v1/forecast.json?key=bcb137dd70f44b329cc154231230704&aqi=yes&alerts=no&q=Paris&dt=2023-03-24";

        ClassPathResource resource = new ClassPathResource("static/expectedJsonAirQualityService.json");
        String strJson = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        JSONObject expectedJson = new JSONObject(strJson);

        Mockito.when(airQualityService.getJson(new URL(parisUrlWithParameters))).thenReturn(expectedJson);

    }

    @Test
    public void whenGetParisAirQualityThenReturnCorrectObject() throws Exception {
        AirQualityRecord airQuality = airQualityService.getAirQuality("Paris", date);
        assertThat(airQuality.getCity()).isEqualTo("Paris");
        assertThat(airQuality.getDate()).isEqualTo(date);
        assertThat(airQuality.getCo()).isEqualTo(238.2);
        assertThat(airQuality.getNo2()).isEqualTo(4.69);
        assertThat(airQuality.getO3()).isEqualTo(75.38);
        assertThat(airQuality.getSo2()).isEqualTo(1.32);
        assertThat(airQuality.getPm2_5()).isEqualTo(2.97);
        assertThat(airQuality.getPm10()).isEqualTo(3.96);
    }

    @Test
    public void whenGetInvalidAirQualityThenReturnInvalidObject() {
        AirQualityRecord airQuality = airQualityService.getAirQuality("Error city", date);
        assertNull(airQuality);

    }

}

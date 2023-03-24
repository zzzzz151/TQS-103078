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

@ExtendWith(MockitoExtension.class)
class AirQualityServiceTests {

    @InjectMocks
    @Spy
    private AirQualityService airQualityService;

    private static LocalDate date = LocalDate.of(2023, 3, 24);

    @BeforeEach
    public void setUp() throws Exception {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=da7106a4963a4260a80141224232303&aqi=yes&alerts=no&q=Paris&dt=2023-03-24";

        ClassPathResource resource = new ClassPathResource("static/expectedJsonAirQualityService.json");
        String strJson = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        JSONObject expectedJson = new JSONObject(strJson);

        Mockito.when(airQualityService.getJson(new URL(url))).thenReturn(expectedJson);

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
    public void whenGetInvalidAirQulityThenReturnInvalidObject()
    {
        AirQualityRecord airQuality1 = airQualityService.getAirQuality("Error city", date);

        assertThat(airQuality1.getCo()).isEqualTo(-1);
        assertThat(airQuality1.getNo2()).isEqualTo(-1);
        assertThat(airQuality1.getSo2()).isEqualTo(-1);
        assertThat(airQuality1.getO3()).isEqualTo(-1);
        assertThat(airQuality1.getPm2_5()).isEqualTo(-1);
        assertThat(airQuality1.getPm10()).isEqualTo(-1);

    }

}

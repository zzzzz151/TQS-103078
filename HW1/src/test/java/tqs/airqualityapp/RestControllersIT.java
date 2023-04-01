package tqs.airqualityapp;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = AirQualityApplication.class)
@AutoConfigureMockMvc
public class RestControllersIT {

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    void whenGetCacheThenReturnCacheStats() throws Exception {
        LocalDate today = LocalDate.now();
        String strToday = Utils.dateToStr(today);
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String strTomorrow = Utils.dateToStr(tomorrow);

        mvc.perform(get("/api/v1/air-quality?city=London&date=" + strTomorrow)); // miss
        mvc.perform(get("/api/v1/air-quality?city=London&date=" + strToday)); // miss
        mvc.perform(get("/api/v1/air-quality?city=London&date=" + strTomorrow)); // hit
        mvc.perform(get("/api/v1/air-quality?city=Paris&date=" + strTomorrow)); // miss
        mvc.perform(get("/api/v1/air-quality?city=London&date=" + strTomorrow)); // hit

        mvc.perform(get("/api/v1/cache")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requests").isNumber())
                .andExpect(jsonPath("$.requests").value(5))
                .andExpect(jsonPath("$.hits").isNumber())
                .andExpect(jsonPath("$.hits").value(2))
                .andExpect(jsonPath("$.misses").isNumber())
                .andExpect(jsonPath("$.misses").value(3))
                .andExpect(jsonPath("$.timeToLiveSeconds").isNumber())
                .andExpect(jsonPath("$.timeToLiveSeconds").value(greaterThanOrEqualTo(0)));
    }

    @Test
    @Order(2)
    void testGetAirQuality() throws Exception {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String strTomorrow = Utils.dateToStr(tomorrow);

        mvc.perform(get("/api/v1/air-quality?city=London&date=" + strTomorrow)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("London"))
                .andExpect(jsonPath("$.date").value(strTomorrow))
                .andExpect(jsonPath("$.co").isNumber())
                .andExpect(jsonPath("$.co").value(greaterThanOrEqualTo(0.0)))
                .andExpect(jsonPath("$.no2").isNumber())
                .andExpect(jsonPath("$.no2").value(greaterThanOrEqualTo(0.0)))
                .andExpect(jsonPath("$.o3").isNumber())
                .andExpect(jsonPath("$.o3").value(greaterThanOrEqualTo(0.0)))
                .andExpect(jsonPath("$.so2").isNumber())
                .andExpect(jsonPath("$.so2").value(greaterThanOrEqualTo(0.0)))
                .andExpect(jsonPath("$.pm2_5").isNumber())
                .andExpect(jsonPath("$.pm2_5").value(greaterThanOrEqualTo(0.0)))
                .andExpect(jsonPath("$.pm10").isNumber())
                .andExpect(jsonPath("$.pm10").value(greaterThanOrEqualTo(0.0)));

        mvc.perform(get("/api/v1/air-quality?city=ErrorCity&date=" + strTomorrow)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        mvc.perform(get("/api/v1/air-quality?city=London&date=123456")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

}

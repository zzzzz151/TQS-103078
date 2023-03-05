package tqs.lab3_2;

import tqs.lab3_2.Car;
import tqs.lab3_2.CarController;
import tqs.lab3_2.JsonUtils;

import org.springframework.http.MediaType;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    private Car myCar;

    @BeforeEach
    public void setUp() throws Exception {
        myCar = new Car("Audi", "A4");
    }

    @Test
    public void whenPostCar_thenCreateCar() throws Exception{
        when(service.save(Mockito.any())).thenReturn(myCar);

        mvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(myCar )))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.maker", is(myCar .getMaker())))
            .andExpect(jsonPath("$.model", is(myCar .getModel())));
        
        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    public void whenGetCars_thenReturnJsonArray() throws Exception {

        Car mustang = new Car("Ford", "Mustang");
        Car punto = new Car("Fiat", "Punto");

        List<Car> cars = Arrays.asList(myCar, mustang, punto);

        when(service.getAllCars()).thenReturn(cars);

        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is(myCar.getMaker())))
                .andExpect(jsonPath("$[0].model", is(myCar.getModel())))
                .andExpect(jsonPath("$[1].maker", is(mustang.getMaker())))
                .andExpect(jsonPath("$[1].model", is(mustang.getModel())))
                .andExpect(jsonPath("$[2].maker", is(punto.getMaker())))
                .andExpect(jsonPath("$[2].model", is(punto.getModel())));

        verify(service, VerificationModeFactory.times(1)).getAllCars();

    }

    @Test
    public void whenGetCar_thenReturnJson() throws Exception{
        when(service.getCar(myCar.getId())).thenReturn(Optional.of(myCar));

        String path = "/api/cars/" + myCar.getId();

        mvc.perform(get(path).contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(myCar)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.maker", is(myCar.getMaker())))
            .andExpect(jsonPath("$.model", is(myCar.getModel())));
        
        verify(service, times(1)).getCar(myCar.getId());
    }
}
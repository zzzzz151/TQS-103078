package tqs.lab7_2;

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
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import tqs.lab7_2.Car;
import tqs.lab7_2.CarManagerService;
import java.util.ArrayList;
import static org.hamcrest.Matchers.hasSize;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import java.io.IOException;
import java.util.Optional;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenPostCars_thenGetCarsAsJsonArray() {
        Car car = new Car("Audi", "A4");
        Car car2 = new Car("Ford", "Mustang");
        ArrayList<Car> cars = new ArrayList<Car>();
        cars.add(car);
        cars.add(car2);
        when(service.save(car)).thenReturn(car);
        when(service.getAllCars()).thenReturn(cars);

        RestAssuredMockMvc.given()
                .header("Content-type", "application/json")
                .and()
                .body(car)
                .when()
                .post("/api/cars")
                .then()
                .statusCode(201);

        RestAssuredMockMvc.given()
                .header("Content-type", "application/json")
                .and()
                .body(car2)
                .when()
                .post("/api/cars")
                .then()
                .statusCode(201);

        RestAssuredMockMvc.when()
                .get("/api/cars")
                .then()
                .body("[0].maker", is("Audi"))
                .body("[0].model", is("A4"))
                .body("[1].maker", is("Ford"))
                .body("[1].model", is("Mustang"));

    }

    @Test
    public void whenPostCar_thenGetCarAsJsonObject() throws Exception {
        Car car = new Car("Audi", "A4");
        car.setId(950);
        when(service.save(car)).thenReturn(car);
        when(service.getCar(950)).thenReturn(Optional.of(car));


        RestAssuredMockMvc.given()
                .header("Content-type", "application/json")
                .and()
                .body(car)
                .when()
                .post("/api/cars")
                .then()
                .statusCode(201);

        RestAssuredMockMvc.when()
                .get("/api/cars/950")
                .then()
                .body("maker", is("Audi"))
                .body("model", is("A4"));
    }
}

package tqs.lab3_3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import tqs.lab3_3.Car;
import tqs.lab3_3.CarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase
// switch AutoConfigureTestDatabase with TestPropertySource to use a real
// database
@TestPropertySource( locations = "classpath:application-integrationtest.properties")
class CarRestControllerTemplateIT {

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    // a REST client that is test-friendly
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car myCar = new Car("Audi", "A4");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", myCar, Car.class);

        List<Car> found = repository.findAll();
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getMaker()).isEqualTo("Audi");
        assertThat(found.get(0).getModel()).isEqualTo("A4");
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        Car car1 = new Car("Audi", "A4");
        Car car2 = new Car("Ford", "Mustang");
        repository.saveAndFlush(car1);
        repository.saveAndFlush(car2);

        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/cars",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Car>>() {
                        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(car1, car2);

    }

}

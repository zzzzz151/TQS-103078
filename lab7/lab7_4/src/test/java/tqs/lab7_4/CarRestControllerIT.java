// mvn clean install
// mvn failsafe:integration-test

package tqs.lab7_4;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import tqs.lab7_4.Car;
import tqs.lab7_4.CarRepository;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
// @TestPropertySource(locations =
// "classpath:application-integrationtest.properties")
class CarRestControllerIT {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
            .withUsername("ric")
            .withPassword("password")
            .withDatabaseName("test");

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void postCarsThenGetCars() throws IOException {
        Car car1 = new Car(1, "Audi", "A4");
        Car car2 = new Car(2, "Ford", "Mustang");

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(randomServerPort)
                .pathSegment("api", "cars")
                .build()
                .toUriString();

        RestAssured.given().auth().none().contentType("application/json")
                .body(JsonUtils.toJson(car1))
                .when().post(endpoint)
                .then().statusCode(201);

        RestAssured.given().auth().none().contentType("application/json")
                .body(JsonUtils.toJson(car2))
                .when().post(endpoint)
                .then().statusCode(201);

        List<Car> cars = repository.findAll();
        assertEquals(cars.get(0), car1);
        assertEquals(cars.get(1), car2);

        RestAssured.given().auth().none().contentType("application/json")
                .get(endpoint)
                .then().statusCode(200)
                .body("size()", is(2))
                .body("[0].maker", is("Audi"))
                .body("[0].model", is("A4"))
                .body("[1].maker", is("Ford"))
                .body("[1].model", is("Mustang"));

    }

}

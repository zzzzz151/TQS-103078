package tqs.lab7_3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
class ApplicationTests {

  @Container
  public static PostgreSQLContainer container = new PostgreSQLContainer()
      .withUsername("duke")
      .withPassword("password")
      .withDatabaseName("test");

  @Autowired
  private CarRepository repository;

  // requires Spring Boot >= 2.2.6
  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void contextLoads() {
    Car car = new Car("Audi", "A4");
    repository.save(car);
    System.out.println("Context loads!");
  }

  @Test
  void insertThenReadCar()
  {
    Car car = new Car("Ford", "Mustang");
    car.setId(800);
    repository.save(car);
    Car retrieved = repository.findById((long)800).get();
    assertEquals(car, retrieved);
  }
}


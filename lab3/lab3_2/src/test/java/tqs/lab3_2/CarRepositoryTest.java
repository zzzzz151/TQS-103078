package tqs.lab3_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs.lab3_2.Car;
import tqs.lab3_2.CarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTest {

    // get a test-friendly Entity Manager
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository repository;

    private Car myCar;

    @Test
    void whenFindCarByMakerAndModel_thenReturnCar() {
        // insert car in db
        myCar = new Car("Audi", "A4");
        entityManager.persistAndFlush(myCar); // ensure data is persisted at this point

        Car found = repository.findByMakerAndModel(myCar.getMaker(), myCar.getModel());
        assertThat(found).isEqualTo(myCar);
    }

    @Test
    void whenInvalidCarMakerOrModel_thenReturnNull() {
        // insert car in db
        myCar = new Car("Audi", "A4");
        entityManager.persistAndFlush(myCar); // ensure data is persisted at this point

        Car carFromDb = repository.findByMakerAndModel("wrong", "wrong");
        assertThat(carFromDb).isNull();
        carFromDb = repository.findByMakerAndModel("Audi", "wrong");
        assertThat(carFromDb).isNull();
        carFromDb = repository.findByMakerAndModel("wrong", "A4");
        assertThat(carFromDb).isNull();

    }

    @Test
    void givenCars_whenFindAll_thenReturnCars() {
        Car car1 = new Car("Mazda", "RX8");
        Car car2 = new Car("Fiat", "Punto");
        Car car3 = new Car("Ford", "Mustang");
        entityManager.persist(car1);
        entityManager.persist(car2);
        entityManager.persist(car3);
        entityManager.flush();

        assertThat(repository.findAll())
                .hasSize(3)
                .containsOnly(car1, car2, car3);
    }

}
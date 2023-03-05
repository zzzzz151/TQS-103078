package tqs.lab3_3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.lab3_3.Car;
import tqs.lab3_3.CarRepository;
import tqs.lab3_3.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock(lenient = true)
    private CarRepository repository;

    @InjectMocks
    private CarManagerService service;

    private Car car1, car2, car3;
    private List<Car> allCars;

    @BeforeEach
    public void setUp() {
        car1 = new Car("Audi", "A4");
        car2 = new Car("Ford", "Mustang");
        car3 = new Car("Ford", "Mustang");

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        Mockito.when(repository.findByMakerAndModel(car1.getMaker(), car1.getModel())).thenReturn(car1);
        Mockito.when(repository.findByMakerAndModel(car2.getMaker(), car2.getModel())).thenReturn(car2);
        Mockito.when(repository.findByMakerAndModel(car3.getMaker(), car3.getModel())).thenReturn(car3);
        Mockito.when(repository.findAll()).thenReturn(allCars);

    }

    @Test
    void whenSearchValidMakerAndModel_thenCarShouldBeFound() {
        Car found = service.getCarByMakerAndModel("Ford", "Mustang").get();

        assertThat(found.getMaker()).contains("Ford");
        assertThat(found.getModel()).contains("Mustang");

        Mockito.verify(repository, VerificationModeFactory.times(1))
                .findByMakerAndModel(Mockito.anyString(), Mockito.anyString());

    }

    @Test
    void whenSearchInvalidMakerAndModel_thenCarShouldNotBeFound() {
        Mockito.when(repository.findByMakerAndModel("wrong maker", "wrong model")).thenReturn(null);

        Car invalidCar = service.getCarByMakerAndModel("wrong maker", "wrong model").orElse(null);

        assertThat(invalidCar).isNull();

        Mockito.verify(repository, VerificationModeFactory.times(1))
                .findByMakerAndModel(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    void given3Cars_whenGetAll_return3Cars() {
        assertThat(service.getAllCars())
                .hasSize(3)
                .contains(car1, car2, car3);

        Mockito.verify(repository, VerificationModeFactory.times(1))
                .findAll();
    }

}

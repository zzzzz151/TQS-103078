package tqs.carsapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import tqs.carsapp.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByMakerAndModel(String maker, String model);

    List<Car> findAll();
}

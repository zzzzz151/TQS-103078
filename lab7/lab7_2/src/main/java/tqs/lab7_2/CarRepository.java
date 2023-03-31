package tqs.lab7_2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import tqs.lab7_2.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByMakerAndModel(String maker, String model);

    List<Car> findAll();
}

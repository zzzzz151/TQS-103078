package tqs.lab7_4;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.lab7_4.CarRepository;
import tqs.lab7_4.Car;

@Service
public class CarManagerService {

   @Autowired
   private CarRepository repository;

   public Car save(Car car) {
      return repository.save(car);
   }

   public List<Car> getAllCars() {
      return repository.findAll();
   }

   public Optional<Car> getCar(int carId) {

      return repository.findById(carId);
   }

   public Optional<Car> getCarByMakerAndModel(String maker, String model)
   {
      return Optional.ofNullable(repository.findByMakerAndModel(maker, model));
   }

}

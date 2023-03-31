package tqs.lab7_2;

import tqs.lab7_2.CarManagerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarManagerService service;

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = service.save(car);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping(path = "/cars")
    public List<Car> getAllCars() {
        return service.getAllCars();
    }

    @GetMapping(path = "/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Car car = service.getCar(id).orElse(null);
        if (car != null)
            status = HttpStatus.OK;
        return new ResponseEntity<>(car, status);
    }

}

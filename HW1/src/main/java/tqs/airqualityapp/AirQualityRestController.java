package tqs.airqualityapp;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AirQualityRestController {

    @Autowired
    private AirQualityService airQualityService;

    @GetMapping("/air-quality")
    public ResponseEntity<?> getAirQuality(@RequestParam(required = true, name = "city", value = "city") String city,
            @RequestParam(required = true, name = "date", value = "date") String date) throws Exception {

        LocalDate dateObj = null;
        try {
            dateObj =  Utils.strToDate(date);
        }
        catch (Exception e)
        {
            CityAndDate cityAndDate = new CityAndDate(city, null);
            return new ResponseEntity<>(cityAndDate, HttpStatus.BAD_REQUEST);
        }

        AirQualityRecord record = airQualityService.getAirQuality(city, Utils.strToDate(date));
        if (record == null) {
            CityAndDate cityAndDate = new CityAndDate(city, Utils.strToDate(date));
            return new ResponseEntity<>(cityAndDate, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

}

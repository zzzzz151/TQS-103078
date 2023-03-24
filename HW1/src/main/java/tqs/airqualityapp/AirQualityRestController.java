package tqs.airqualityapp;

import org.springframework.beans.factory.annotation.Autowired;
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
    public AirQualityRecord getAirQuality(@RequestParam(required = true, name = "city", value = "city") String city,
            @RequestParam(required = true, name = "date", value = "date") String date) throws Exception {

        return airQualityService.getAirQuality(city, Utils.strToDate(date));
    }

}

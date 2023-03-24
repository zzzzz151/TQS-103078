package tqs.airqualityapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyController {

    @Autowired
    private AirQualityService airQualityService;

    @GetMapping("/index")
    public String indexPage() {
        return "redirect:/";
    }

    @GetMapping("/home")
    public String homePage() {
        return "redirect:/";
    }

    @GetMapping("/air-quality")
    public String airQualityPage(@RequestParam(required = true, value = "city", name = "city") String city,
            @RequestParam(required = true, value = "date", name = "date") String date,
            Model model) throws Exception {

        model.addAttribute("city", city);
        model.addAttribute("date", date);

        AirQualityRecord airQualityRecord = airQualityService.getAirQuality(city, Utils.strToDate(date));
        model.addAttribute("airQualityData", airQualityRecord);

        return "air-quality";
    }
}

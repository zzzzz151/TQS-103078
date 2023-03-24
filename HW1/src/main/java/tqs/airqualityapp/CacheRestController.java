package tqs.airqualityapp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class CacheRestController {

    @Autowired
    private AirQualityService airQualityService;

    @GetMapping("/cache")
    public Map<String, Object> getCacheStats()
    {
        HashMap<String, Object> map = new HashMap<>();
        Cache<CityAndDate, AirQualityRecord> cache  = airQualityService.getCache();
        map.put("timeToLiveSeconds", cache.getTimeToLiveSeconds());
        map.put("requests", cache.getRequests());
        map.put("hits", cache.getHits());
        map.put("misses", cache.getMisses());
        return map;
    }

}

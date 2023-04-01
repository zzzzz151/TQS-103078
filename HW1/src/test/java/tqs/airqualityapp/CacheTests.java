package tqs.airqualityapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class CacheTests {

    @Test
    public void testCache() throws InterruptedException {
        AirQualityRecord londonRecord = new AirQualityRecord("London", LocalDate.of(2023, 3, 24));
        londonRecord.setCo(5.71);
        AirQualityRecord newYorkRecord = new AirQualityRecord("New York", LocalDate.of(2023, 3, 25));
        CityAndDate london = new CityAndDate(londonRecord.getCity(), londonRecord.getDate());
        CityAndDate newYork = new CityAndDate(newYorkRecord.getCity(), newYorkRecord.getDate());

        // Assert item TTL is refreshed when hit and that an item is gone after TTL but
        // not before TTL
        Cache<CityAndDate, AirQualityRecord> cache = new Cache<CityAndDate, AirQualityRecord>(1);
        cache.put(london, londonRecord);
        cache.put(newYork, newYorkRecord);
        Thread.sleep(500); // sleep 0.5s
        assertNotNull(cache.get(london)); // hit, so london's TTL should be refreshed
        Thread.sleep(800); // sleep 0.8s
        // Slept a total of 1.3s
        assertNull(cache.get(newYork));
        assertNotNull(cache.get(london));

        assertEquals(cache.getTimeToLiveSeconds(), 1);
        assertEquals(cache.getRequests(), 3);
        assertEquals(cache.getHits(), 2);
        assertEquals(cache.getMisses(), 1);

    }
}

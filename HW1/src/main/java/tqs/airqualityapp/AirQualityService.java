package tqs.airqualityapp;

import java.net.URL;
import java.time.LocalDate;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class AirQualityService {

    private final String apiBaseUrl = "https://api.weatherapi.com/v1/forecast.json?key=da7106a4963a4260a80141224232303&aqi=yes&alerts=no";

    public String addParametersToApiUrl(String city, LocalDate date) {
        return addParametersToApiUrl(city, Utils.dateToStr(date));
    }

    public String addParametersToApiUrl(String city, String date) {
        city = city.replace(" ", "%20");
        //System.out.println(apiBaseUrl + "&q=" + city + "&dt=" + date);
        return apiBaseUrl + "&q=" + city + "&dt=" + date;
    }

    public JSONObject getJson(URL url) {
        String json = "";
        try {
            json = IOUtils.toString(url, Charset.forName("UTF-8"));
        } catch (Exception e) {
            return new JSONObject("{}");
        }
        return new JSONObject(json);
    }

    private int timeToLiveSeconds = 15;

    public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

    private Cache<CityAndDate, AirQualityRecord> cache = new Cache<CityAndDate, AirQualityRecord>(timeToLiveSeconds);

    public Cache<CityAndDate, AirQualityRecord> getCache() {
        return this.cache;
    }

    public AirQualityRecord getAirQuality(String city, LocalDate date) {
        CityAndDate key = new CityAndDate(city, date);
        AirQualityRecord record = cache.get(key);
        if (record != null)
            return record;

        record = new AirQualityRecord(city, date);
        String completeApiUrl = addParametersToApiUrl(city, date);
        JSONObject airQuality;
        try {
            JSONObject json = getJson(new URL(completeApiUrl));
            JSONObject forecastDay = json.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0);
            airQuality = forecastDay.getJSONObject("day").getJSONObject("air_quality");
        } catch (Exception e) {
            return record;
        }
        if (!airQuality.has("co"))
            return record;

        double co = airQuality.getDouble("co");
        double no2 = airQuality.getDouble("no2");
        double o3 = airQuality.getDouble("o3");
        double so2 = airQuality.getDouble("so2");
        double pm2_5 = airQuality.getDouble("pm2_5");
        double pm10 = airQuality.getDouble("pm10");

        record.setCo(Utils.round(co, 2));
        record.setNo2(Utils.round(no2, 2));
        record.setO3(Utils.round(o3, 2));
        record.setSo2(Utils.round(so2, 2));
        record.setPm2_5(Utils.round(pm2_5, 2));
        record.setPm10(Utils.round(pm10, 2));

        cache.put(key, record);
        return record;
    }
}

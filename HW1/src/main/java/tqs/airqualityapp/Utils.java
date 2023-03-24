package tqs.airqualityapp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final Logger logger = LogManager.getLogger(AirQualityApplication.class);

    public static LocalDate strToDate(String date) {
        return LocalDate.parse(date, formatter);

    }

    public static String dateToStr(LocalDate date) {
        return formatter.format(date);
    }

    public static double round(double val, int numDecimalPlaces) {
        double a = Math.pow(10, numDecimalPlaces);
        return Math.round(val * a) / a;
    }

    public static Logger getLogger() {
        return logger;
    }
}

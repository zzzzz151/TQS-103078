package tqs.airqualityapp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Utils {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    public static void log(String message) {
        System.out.println(formatter2.format(LocalDateTime.now()) + " " + message);
    }
}

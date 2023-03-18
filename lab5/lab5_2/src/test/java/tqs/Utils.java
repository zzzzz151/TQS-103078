package tqs;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class Utils {
    public static <T> T mapToObject(Map<String, String> map, Class<T> clazz) {
        T obj;
        try {
            obj = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);

        // iterate through all fields in the target class
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName(); 
            String fieldValue = map.get(fieldName); 

            if (fieldValue != null) {
                field.setAccessible(true); 
                Class<?> fieldType = field.getType();
                Object convertedValue = switch (fieldType.getSimpleName()) {
                    case "String" -> fieldValue;
                    case "int", "Integer" -> Integer.parseInt(fieldValue);
                    case "double", "Double" -> Double.parseDouble(fieldValue);
                    case "boolean", "Boolean" -> Boolean.parseBoolean(fieldValue);
                    case "LocalDate" -> LocalDate.parse(fieldValue, formatter);
                    default -> null;
                };

                try {
                    field.set(obj, convertedValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return obj;
    }
}
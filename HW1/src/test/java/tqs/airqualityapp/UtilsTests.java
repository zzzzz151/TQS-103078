package tqs.airqualityapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class UtilsTests {
    @Test
    public void testStrToDate() 
    {
        String strDate = "2023-05-24";
        LocalDate localDate = Utils.strToDate(strDate);
        assertEquals(localDate.getDayOfMonth(), 24);
        assertEquals(localDate.getMonthValue(), 5);
        assertEquals(localDate.getYear(), 2023);
    
    }

    @Test
    public void testDateToStr () 
    {
        LocalDate localDate = LocalDate.of(2022, 11, 29);
        String strDate = Utils.dateToStr(localDate);
        assertEquals(strDate, "2022-11-29");
    
    }

    @Test
    public void testRound()
    {
        double a = 25.54812384;
        double b = 6.14;
        double c = 50;
        double d = -4.68134;
        assertEquals(Utils.round(a, 2), 25.55);
        assertEquals(Utils.round(b, 1), 6.1);
        assertEquals(Utils.round(c, 1), 50);
        assertEquals(Utils.round(d, 0), -5);

    }
}

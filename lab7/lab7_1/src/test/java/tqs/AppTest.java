package tqs;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private static String url = "https://jsonplaceholder.typicode.com/todos";

    @Test
    public void testStatusCodeIs200()
    {
        get(url).then().statusCode(is(200));

    }

    @Test
    public void tesTodo4()
    {
        get(url + "/4").then().body("title", is("et porro tempora"));

    }

    @Test
    public void testTodo198And199Exist()
    {
        get(url).then().body("id", hasItems(198,199));
    }

    @Test
    public void testResponseTimeLessThan2Seconds()
    {
        get(url).then().time(lessThan(2000L));

    }
}

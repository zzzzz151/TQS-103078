
package tqs.airqualityapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;
import java.time.LocalDate;
import org.openqa.selenium.support.ui.Select;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.bonigarcia.wdm.WebDriverManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumTests {
  @LocalServerPort
  private int port;

  @Autowired
  AirQualityService airQualityService;

  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;

  @BeforeAll
  static void setupClass() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  public void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--remote-allow-origins=*");
    driver = new ChromeDriver(options);
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void forecastDayOptions()
  {
        driver.get("http://localhost:" + port);

        driver.manage().window().setSize(new Dimension(1846, 1040));    
  
        driver.findElement(By.id("selectDay")).click();
    
        // assert day selection options
        WebElement elem = driver.findElement(By.id("selectDay"));
        Select select = new Select(elem);
        List<WebElement> options = select.getOptions();
        assertEquals(options.get(0).getText(), "Today (" + Utils.dateToStr(LocalDate.now()) + ")");
        assertEquals(options.get(1).getText(), "Tomorrow (" + Utils.dateToStr(LocalDate.now().plusDays(1)) + ")");
        assertEquals(options.get(2).getText(), "2 days from now (" + Utils.dateToStr(LocalDate.now().plusDays(2)) + ")");
  }

  @Test
  public void madridTomorrow() {
    // Test name: MadridTomorrow
    // Step # | name | target | value

    String strTomorrow = Utils.dateToStr(LocalDate.now().plusDays(1));

    // 1 | open | http://localhost:8080/ |
    driver.get("http://localhost:" + port);

    // 2 | setWindowSize | 1846x1040 |
    driver.manage().window().setSize(new Dimension(1846, 1040));

    // 3 | click | id=selectCity |
    driver.findElement(By.id("selectCity")).click();

    // 4 | select | id=selectCity | label=Madrid
    {
      WebElement dropdown = driver.findElement(By.id("selectCity"));
      dropdown.findElement(By.xpath("//option[. = 'Madrid']")).click();
    }

    // 5 | click | id=selectDay |
    driver.findElement(By.id("selectDay")).click();

    // 6 | select | id=selectDay | label=Tomorrow (strTomorrow)
    {
      WebElement elem = driver.findElement(By.id("selectDay"));
      Select select = new Select(elem);
      List<WebElement> options = select.getOptions();
      options.get(1).click();
    }

    // 7 | click | css=.btn |
    driver.findElement(By.cssSelector(".btn")).click();

    // 8 | assertText | css=h1 > span:nth-child(1) | Madrid
    assertEquals(driver.findElement(By.cssSelector("h1 > span:nth-child(1)")).getText(), "Madrid");

    // 9 | assertText | css=h1 > span:nth-child(2) | strTomorrow
    assertEquals(driver.findElement(By.cssSelector("h1 > span:nth-child(2)")).getText(), strTomorrow);

    // 10 | assertElementPresent | css=p:nth-child(3) |
    {
      List<WebElement> elements = driver.findElements(By.cssSelector("p:nth-child(3)"));
      assert (elements.size() > 0);
    }

    // 11 | assertElementPresent | css=p:nth-child(4) |
    {
      List<WebElement> elements = driver.findElements(By.cssSelector("p:nth-child(4)"));
      assert (elements.size() > 0);
    }

    // 12 | assertElementPresent | css=p:nth-child(5) |
    {
      List<WebElement> elements = driver.findElements(By.cssSelector("p:nth-child(5)"));
      assert (elements.size() > 0);
    }

    // 13 | assertElementPresent | css=p:nth-child(6) |
    {
      List<WebElement> elements = driver.findElements(By.cssSelector("p:nth-child(6)"));
      assert (elements.size() > 0);
    }

    // 14 | assertElementPresent | css=p:nth-child(7) |
    {
      List<WebElement> elements = driver.findElements(By.cssSelector("p:nth-child(7)"));
      assert (elements.size() > 0);
    }

    // 15 | assertElementPresent | css=p:nth-child(8) |
    {
      List<WebElement> elements = driver.findElements(By.cssSelector("p:nth-child(8)"));
      assert (elements.size() > 0);
    }

    AirQualityRecord record = airQualityService.getAirQuality("Madrid", LocalDate.now().plusDays(1));

    // 16 | assertText | css=p:nth-child(3) > span | co
    assertThat(Double.parseDouble(driver.findElement(By.cssSelector("p:nth-child(3) > span")).getText()))
        .isGreaterThanOrEqualTo(0.0).isEqualTo(record.getCo());

    // 17 | assertText | css=p:nth-child(4) > span | o3
    assertThat(Double.parseDouble(driver.findElement(By.cssSelector("p:nth-child(4) > span")).getText()))
        .isGreaterThanOrEqualTo(0.0).isEqualTo(record.getO3());

    // 18 | assertText | css=p:nth-child(5) > span | no2
    assertThat(Double.parseDouble(driver.findElement(By.cssSelector("p:nth-child(5) > span")).getText()))
        .isGreaterThanOrEqualTo(0.0).isEqualTo(record.getNo2());

    // 19 | assertText | css=p:nth-child(6) > span | so2
    assertThat(Double.parseDouble(driver.findElement(By.cssSelector("p:nth-child(6) > span")).getText()))
        .isGreaterThanOrEqualTo(0.0).isEqualTo(record.getSo2());

    // 20 | assertText | css=p:nth-child(7) > span | pm2.5
    assertThat(Double.parseDouble(driver.findElement(By.cssSelector("p:nth-child(7) > span")).getText()))
        .isGreaterThanOrEqualTo(0.0).isEqualTo(record.getPm2_5());

    // 21 | assertText | css=p:nth-child(8) > span | pm10
    assertThat(Double.parseDouble(driver.findElement(By.cssSelector("p:nth-child(8) > span")).getText()))
        .isGreaterThanOrEqualTo(0.0).isEqualTo(record.getPm10());
  }
}

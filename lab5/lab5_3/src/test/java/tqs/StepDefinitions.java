package tqs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.hamcrest.CoreMatchers.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import io.github.bonigarcia.wdm.WebDriverManager;


public class StepDefinitions {

    private WebDriver driver;

    @Given("I am in the initial page")
    public void inInitialPage() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1200,800));
        driver.get("https://blazedemo.com/");
    }

    @When("I select the trip from {string} to {string}")
    public void selectCities(String cityFrom, String cityTo) {
        //System.out.println("------------- " + cityFrom + " TO " + cityTo + "--------------");
        
        driver.findElement(By.name("fromPort")).click();
        WebElement fromDropdown = driver.findElement(By.name("fromPort"));
        fromDropdown.findElement(By.xpath("//option[. = '" + cityFrom + "']")).click();

        driver.findElement(By.name("toPort")).click();
        WebElement toDropdown = driver.findElement(By.name("toPort"));
        toDropdown.findElement(By.xpath("//option[. = '" + cityTo + "']")).click();
    }

    @When("I click 'Find Flights'")
    public void clickFindFlights() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @When("I select flight number {int}")
    public void selectFlightNumber(Integer flightNumber) {
        String cssSelector = "tr:nth-child(" + flightNumber + ") .btn";
        driver.findElement(By.cssSelector(cssSelector)).click();
    }

    @When("I fill my passenger details with my name {string}")
    public void fillPassengerDetails(String customerName) {
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys(customerName);
    }

    @When("I hit 'Confirm'")
    public void hitConfirm() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("I am on the confirmation page")
    public void onTheConfirmationPage() {
        assertThat(driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
    }

    @Then("I should see the price")
    public void priceIsShown() {
        WebElement priceElement = driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(2)"));
        //assertThat(priceElement, is(notNullValue()));
        assertNotNull(priceElement);
    }

    @AfterAll
    public void quitBrowser()
    {
        driver.quit();
    }

}

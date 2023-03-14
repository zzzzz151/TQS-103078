package tqs;

import tqs.pages.ConfirmationPage;
import tqs.pages.HomePage;
import tqs.pages.PurchasePage;
import tqs.pages.ReservePage;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class BlazerunnerTest {

    private ChromeDriver driver;
    private HomePage homePage;
    private ReservePage reservePage;
    private PurchasePage purchasePage;
    private ConfirmationPage confirmationPage;

    @BeforeEach
    public void initializeDriver(ChromeDriver driver) {
        this.driver = driver;
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(1294, 741));
        homePage = new HomePage(driver);
        reservePage = new ReservePage(driver);
        purchasePage = new PurchasePage(driver);
        confirmationPage = new ConfirmationPage(driver);
    }

    @Test
    public void blazerunner() {
        homePage.selectDepartureCity("Mexico City");
        homePage.selectDepartureCity("Paris");
        homePage.selectDestinationCity("Rome");

        homePage.clickFindFlights();

        reservePage.clickChooseThisFlight();

        purchasePage.fillOutPersonalInformation("Ricardo", "", "", "", "");
        purchasePage.fillOutPaymentInformation("", "", "");

        purchasePage.clickPurchaseFlight();
        
        assertThat(confirmationPage.getPendingCapture()).isEqualTo("PendingCapture");
        assertThat(confirmationPage.getTabTitle()).isEqualTo("BlazeDemo Confirmation");
        assert(confirmationPage.get_h1().size() > 0);
    }
}

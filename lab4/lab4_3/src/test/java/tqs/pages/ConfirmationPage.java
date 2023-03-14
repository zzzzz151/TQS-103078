package tqs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class ConfirmationPage {

    private final WebDriver driver;

    @FindBy(css = "tr:nth-child(2) > td:nth-child(2)")
    private WebElement pendingCapture;

    @FindBy(css = "h1")
    private List<WebElement> h1;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPendingCapture()
    {
        return pendingCapture.getText();
    }

    public String getTabTitle() {
        return driver.getTitle();
    }

    public List<WebElement> get_h1()
    {
        return h1;
    }
}

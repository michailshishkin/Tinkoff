package Tinkoff.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class StartPage {

    public StartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public WebDriver driver;

    @FindBy(css = "div.Tabs__item_y4Mo9:nth-child(6) > a:nth-child(1) > span:nth-child(1)")
    private WebElement paymentItem;

    public void clickPaymentItem() {
        paymentItem.click();
    }
}

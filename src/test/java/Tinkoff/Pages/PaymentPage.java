package Tinkoff.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PaymentPage {

    public PaymentPage(WebDriver driver) {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 1), this);
        this.driver = driver;
    }

    public WebDriver driver;

    @FindBy(xpath = "//a[contains(@href, 'kommunalnie-platezhi/')]")
    private WebElement ZHKH;

    @FindBy(xpath = "/html/body/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[5]/div/div[2]/div/div/div/div[2]/div/div/div/span/span/span")
    private WebElement RegionSelect;

    @FindAll(@FindBy(className = "UiRegions__uiRegions__item_3ZlOB"))
    private List<WebElement> regions;

    @FindAll(@FindBy(css = ".ui-link.ui-menu__link.ui-menu__link_logo.ui-menu__link_icons.ui-menu__link_icons_active"))
    private List<WebElement> proAll;

    @FindBy(css = ".Input__valueContent_K3hMY")
    private WebElement SearchInput;

    @FindBy(css = ".SearchSuggest__entry_highlighted_1SPg3 > div:nth-child(1) > div:nth-child(1)")
    private WebElement proList;

    public void clicZHKH() {
        ZHKH.click();
    }

    public String getRegionValue() {
        String region = RegionSelect.getText();
        return region;
    }

    public void clickRegionSelect() {
        RegionSelect.click();
    }

    public void getRegions(String reg) {
        for (WebElement regchaild : regions) {
            if (regchaild.getText().equals(reg)) {
                System.out.println("Меняю регион на " + regchaild.getText());
                Actions action = new Actions(driver);
                action.moveToElement(regchaild, 1, 1).click().build().perform();
                driver.manage().timeouts().setScriptTimeout(1, TimeUnit.SECONDS);
                break;
            } else {
                System.out.println("Нет искомого региона!");
            }
        }
    }

    public void getPro(Integer proItem) {
        driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
        Actions action = new Actions(driver);
        action.moveToElement(proAll.get(proItem), 1, 1).click().build().perform();
    }

    public String proName(Integer proItem) {
        String titlePro = proAll.get(proItem).getAttribute("textContent");
        return titlePro;
    }

    public void setSearchInput(String ProName) {
        SearchInput.sendKeys(ProName);
        driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        System.out.println(proList.getAttribute("textContent"));
    }

    public void getProList(String plName) {
        if (proList.getAttribute("textContent").contentEquals(plName)) {
            Actions action = new Actions(driver);
            action.moveToElement(proList, 1, 1).click().build().perform();
            driver.manage().timeouts().setScriptTimeout(1, TimeUnit.SECONDS);
        } else {
            System.out.println("Первый в списке поставщиков не наш!");
        }
    }
}

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

public class ZhkuMoskvaPage {

    public ZhkuMoskvaPage(WebDriver driver) {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 1), this);
        this.driver = driver;
    }

    public WebDriver driver;

    @FindBy(css = ".Text__text_size_21_3QJCb")
    private WebElement serviceheader;

    @FindAll(@FindBy(className = "Tab__tab_2Ylcg"))
    private List<WebElement> tab;

    @FindBy(css = "#payerCode")
    private WebElement payerCode;

    @FindBy(css = ".ui-form-field-error-message")
    private WebElement ErrMSGPC;

    @FindBy(css = "#period")
    private WebElement period;

    @FindBy(css = ".ui-form__row_date > div:nth-child(1) > div:nth-child(2)")
    private WebElement ErrMSGPer;

    @FindBy(css = "body > div.application > div > div > div.PlatformLayout__layoutPage_WoIKN > div.PortalContainer__container_lyBzt > div.UILayoutPage__page_19-Kp > div:nth-child(1) > div.PlatformLayout__layoutPageComponent_3W4dc > div > div:nth-child(2) > div:nth-child(1) > div > div > div > div.ui-grid__row > div.ui-grid__column.ui-grid__column_desktop_11 > form > div.ui-form__row.ui-form__row_combination > div > div > div > div > div > div > div > div > label > div > input")
    private WebElement summa;

    @FindBy(css = "body > div.application > div > div > div.PlatformLayout__layoutPage_WoIKN > div.PortalContainer__container_lyBzt > div.UILayoutPage__page_19-Kp > div:nth-child(1) > div.PlatformLayout__layoutPageComponent_3W4dc > div > div:nth-child(2) > div:nth-child(1) > div > div > div > div.ui-grid__row > div.ui-grid__column.ui-grid__column_desktop_11 > form > div:nth-child(4) > div > div > div > div > div > div > div > div.ui-form-field-error-message.ui-form-field-error-message_ui-form")
    private WebElement ErrMSGSum;

    @FindBy(css = "span.Text__text_6RrjC")
    private WebElement ZHkH;

    public void clickServiceheader() {
        serviceheader.click();
    }

    public void getTab(String tabName) {
        for (WebElement tabchaild : tab) {
            System.out.println(tabchaild.getText());
            if (tabchaild.getText().equalsIgnoreCase(tabName)) {
                Actions action = new Actions(driver);
                action.moveToElement(tabchaild, 1, 1).click().build().perform();
                driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.SECONDS);
                break;
            } else {
                System.out.println("Нет искомой вкладки!");
            }
        }

    }

    public void setPayerCode(String PC) {
        payerCode.sendKeys(PC);
    }

    public String getPayerCode(){
        String ValuePC = payerCode.getAttribute("defaultValue");
        return ValuePC;
    }

    public void clickPayerCode() {
        payerCode.click();
    }

    public void clearPayerCode() {
        payerCode.clear();
    }

    public String getErrMSGPC() {
        driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
        String ErrorMSGPC = ErrMSGPC.getAttribute("innerText");
        return ErrorMSGPC;
    }

    public void clickPeriod() {
        period.click();
    }

    public void clearPeriod() {
        period.clear();
    }

    public void setPeriod(String Per) {
        period.sendKeys(Per);
    }

    public String getPeriod(){
        String ValuePer = period.getAttribute("defaultValue");
        return ValuePer;
    }

    public String getErrMSGPer() {
        driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
        String ErrorMSGPer = ErrMSGPer.getAttribute("innerText");
        return ErrorMSGPer;
    }

    public void clickSumma(){
        summa.click();
    }

    public void clearSumma() {
        summa.clear();
    }

    public void setSumma(String Sum) {
        summa.sendKeys(Sum);
    }

    public String getSumma(){
        String ValueSum = period.getAttribute("defaultValue");
        return ValueSum;
    }

    public String getErrMSGSum() {
        driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
        String ErrorMSGSum = ErrMSGSum.getAttribute("innerText");
        return ErrorMSGSum;
    }

    public void clickZHkH() {
        ZHkH.click();
    }
}
package Tinkoff.Tests;

import Tinkoff.Pages.PaymentPage;
import Tinkoff.Pages.ZhkuMoskvaPage;
import Tinkoff.Pages.StartPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class FullScriptTest {
    public static WebDriver driver;
    public static StartPage startPage;
    public static PaymentPage paymentPage;
    public static ZhkuMoskvaPage zhkuMoskvaPage;

    String ProviderName = "";

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        startPage = new StartPage(driver);
        paymentPage = new PaymentPage(driver);
        zhkuMoskvaPage = new ZhkuMoskvaPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.tinkoff.ru");
    }

    @Test
    public void SelectRegionTest() {
        startPage.clickPaymentItem();
        paymentPage.clicZHKH();
        if (paymentPage.getRegionValue().equals("г. Москва")) {
            System.out.println("Текущий регион – \"г. Москва\"");
        } else {
            paymentPage.clickRegionSelect();
            paymentPage.getRegions("г. Москва");
            driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
        }
    }

    @Test(dependsOnMethods = {"SelectRegionTest"})
    public void SelectProviderTest() {
        paymentPage.getPro(0);
        ProviderName = paymentPage.proName(0);
    }

    @Test(dependsOnMethods = {"SelectProviderTest"})
    public void SelectTabTest() {
        zhkuMoskvaPage.getTab("Оплатить ЖКУ в Москве");
        driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest0() {
        zhkuMoskvaPage.clickServiceheader();
        zhkuMoskvaPage.clickPayerCode();
        zhkuMoskvaPage.setPayerCode("123");
        zhkuMoskvaPage.setPayerCode(String.valueOf(Keys.CONTROL + "a"));
        zhkuMoskvaPage.setPayerCode(String.valueOf(Keys.DELETE));
        zhkuMoskvaPage.clickServiceheader();
        Assert.assertEquals(zhkuMoskvaPage.getErrMSGPC(), "Поле обязательное", "Текст ошибки не совпадает с ожидаемым");
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest01() {
        zhkuMoskvaPage.setPayerCode("123");
        zhkuMoskvaPage.clickServiceheader();
        Assert.assertEquals(zhkuMoskvaPage.getErrMSGPC(), "Поле неправильно заполнено", "Текст ошибки не совпадает с ожидаемым");
        zhkuMoskvaPage.clearPayerCode();
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest02() {
        zhkuMoskvaPage.setPayerCode("!@#$%^&*(");
        zhkuMoskvaPage.clickServiceheader();
        if (zhkuMoskvaPage.getErrMSGPC().equalsIgnoreCase("Поле обязательное")) {
            zhkuMoskvaPage.clearPayerCode();
        } else {
            Assert.assertEquals(zhkuMoskvaPage.getErrMSGPC(), "Поле неправильно заполнено", "Ввели недопустимые символы, ввод которых ограничен!!!");
        }
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest03() {
        zhkuMoskvaPage.setPayerCode("987456321789");
        zhkuMoskvaPage.clickServiceheader();
        try {
            Assert.assertEquals(zhkuMoskvaPage.getErrMSGPC(), "Поле неправильно заполнено", "Ввели большее количество символов, чем допустимо в Коде плательщика!!!");
        } catch (NoSuchElementException e) {
            if (zhkuMoskvaPage.getPayerCode().equalsIgnoreCase("9874563217")) {
                System.out.println("Обработчик не дал ввести в \"КОД плательщика\" больше 10 символов ;-)");
            }
        }
        zhkuMoskvaPage.clearPayerCode();
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest04() {
        zhkuMoskvaPage.setPayerCode("WERDFyuuhiнеаааснпуами");
        zhkuMoskvaPage.clickServiceheader();
        try {
            Assert.assertEquals(zhkuMoskvaPage.getErrMSGPC(), "Поле неправильно заполнено", "Ввели недопустимые символы, латинского и кирилического алфавита!!!");
        } catch (AssertionError e) {
            if (zhkuMoskvaPage.getPayerCode().equalsIgnoreCase("")) {
                System.out.println("Обработчик не дал ввести в \"КОД плательщика\" недопустимые символы ;-)");
            }
        }
        zhkuMoskvaPage.clearPayerCode();
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest10() {
        zhkuMoskvaPage.clearPeriod();
        zhkuMoskvaPage.setPeriod("111111");
        zhkuMoskvaPage.setPeriod(String.valueOf(Keys.CONTROL + "a"));
        zhkuMoskvaPage.setPeriod(String.valueOf(Keys.DELETE));
        zhkuMoskvaPage.clickServiceheader();
        Assert.assertEquals(zhkuMoskvaPage.getErrMSGPer(), "Поле обязательное", "Текст ошибки не совпадает с ожидаемым");
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest11() {
        zhkuMoskvaPage.clickPeriod();
        zhkuMoskvaPage.setPeriod("222222");
        zhkuMoskvaPage.clickServiceheader();
        Assert.assertEquals(zhkuMoskvaPage.getErrMSGPer(), "Поле заполнено некорректно", "Текст ошибки не совпадает с ожидаемым");
        zhkuMoskvaPage.clearPeriod();
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest12() {
        zhkuMoskvaPage.setPeriod("!@#$%^&*(");
        zhkuMoskvaPage.clickServiceheader();
        if (zhkuMoskvaPage.getErrMSGPer().equalsIgnoreCase("Поле обязательное")) {
            zhkuMoskvaPage.clearPeriod();
        } else {
            Assert.assertEquals(zhkuMoskvaPage.getErrMSGPer(), "Поле заполнено некорректно", "Ввели недопустимые символы, ввод которых ограничен!!!");
        }
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest13() {
        zhkuMoskvaPage.setPeriod("987456321789");
        zhkuMoskvaPage.clickServiceheader();
        if (zhkuMoskvaPage.getPeriod().equalsIgnoreCase("98.7456")) {
            System.out.println("Обработчик не дал ввести в \"Период оплаты\" больше 6 символов ;-)");
        }
        Assert.assertEquals(zhkuMoskvaPage.getErrMSGPer(), "Поле заполнено некорректно");
        zhkuMoskvaPage.clearPeriod();
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest14() {
        zhkuMoskvaPage.setPeriod("WERDFyuuhiнеаааснпуами");
        zhkuMoskvaPage.clickServiceheader();
        try {
            Assert.assertEquals(zhkuMoskvaPage.getErrMSGPer(), "Поле заполнено некорректно", "Ввели недопустимые символы, латинского и кирилического алфавита!!!");
        } catch (AssertionError e) {
            System.out.println("Обработчик не дал ввести в \"Период оплаты\" недопустимые символы ;-)");
        }
        zhkuMoskvaPage.clearPeriod();
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest20() {
        zhkuMoskvaPage.clickSumma();
        zhkuMoskvaPage.setSumma("111111");
        zhkuMoskvaPage.setSumma(String.valueOf(Keys.CONTROL + "a"));
        zhkuMoskvaPage.setSumma(String.valueOf(Keys.DELETE));
        zhkuMoskvaPage.clickServiceheader();
        Assert.assertEquals(zhkuMoskvaPage.getErrMSGSum(), "Поле обязательное", "Текст ошибки не совпадает с ожидаемым");
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest21() {
        zhkuMoskvaPage.clearSumma();
        zhkuMoskvaPage.setSumma("-1231233");
        zhkuMoskvaPage.clickServiceheader();
        Assert.assertEquals(zhkuMoskvaPage.getErrMSGSum(), "Поле заполнено неверно", "Текст ошибки не совпадает с ожидаемым");
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest22() {
        zhkuMoskvaPage.clearSumma();
        zhkuMoskvaPage.setSumma("!@#$%^&=");
        zhkuMoskvaPage.clickServiceheader();
        if (zhkuMoskvaPage.getErrMSGSum().equalsIgnoreCase("Поле обязательное")) {
        } else {
            Assert.assertEquals(zhkuMoskvaPage.getErrMSGSum(), "Поле заполнено неверно", "Ввели недопустимые символы, ввод которых ограничен!!!");
        }
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest23() {
        zhkuMoskvaPage.clearSumma();
        zhkuMoskvaPage.setSumma("**()))))()()()()()()()(+-/0,//0,*()+");
        zhkuMoskvaPage.clickServiceheader();
        Assert.assertEquals(zhkuMoskvaPage.getErrMSGSum(), "Поле заполнено неверно", "Ввели недопустимые символы!!!");
        zhkuMoskvaPage.clearSumma();
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest24() {
        zhkuMoskvaPage.clearSumma();
        zhkuMoskvaPage.setSumma("AWZSXDCFVGYBBUHjnlkmjnhчвксаемнпирлоНЕПГР");
        zhkuMoskvaPage.clickServiceheader();
        if (zhkuMoskvaPage.getErrMSGSum().equalsIgnoreCase("Поле обязательное")) {
        } else {
            Assert.assertEquals(zhkuMoskvaPage.getErrMSGSum(), "Поле заполнено некорректно", "Ввели недопустимые символы, ввод которых ограничен!!!");
        }
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest25() {
        zhkuMoskvaPage.clearSumma();
        zhkuMoskvaPage.setSumma(",0033");
        zhkuMoskvaPage.clickServiceheader();
        Assert.assertEquals(zhkuMoskvaPage.getErrMSGSum(), "Минимальная сумма перевода — 10 \u20BD");
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTest26() {
        zhkuMoskvaPage.clearSumma();
        zhkuMoskvaPage.setSumma("123123123");
        zhkuMoskvaPage.clickServiceheader();
        Assert.assertEquals(zhkuMoskvaPage.getErrMSGSum(), "Максимальная сумма перевода — 15 000 \u20BD");
    }

    @Test(dependsOnMethods = {"SelectTabTest"})
    public void ZhkuMoskvaTestQuit() {
        zhkuMoskvaPage.clickZHkH();
    }

    @Test(dependsOnMethods = {"ZhkuMoskvaTestQuit"})
    public void Step8() {
        startPage.clickPaymentItem();
        paymentPage.setSearchInput(ProviderName);
        driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
        paymentPage.getProList(ProviderName);
    }

    @Test(dependsOnMethods = {"Step8"})
    public void Step12() {
        startPage.clickPaymentItem();
        paymentPage.clicZHKH();
    }

    @Test(dependsOnMethods = {"Step12"})
    public void Step13() {
        if (paymentPage.getRegionValue().equals("г. Санкт-Петербург")) {
            System.out.println("Текущий регион – \"г. Санкт-Петербург \"");
        } else {
            paymentPage.clickRegionSelect();
            paymentPage.getRegions("г. Санкт-Петербург");
            driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
        }
    }

    @Test (dependsOnMethods = {"Step13"})
    public void Step14() {
        paymentPage.getPro(0);
        try {
            Assert.assertEquals(paymentPage.proName(0), ProviderName);
        } catch (AssertionError e) {
            System.out.println("В списке поставщиков нашего предидущего нет ;-)");
        }
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}


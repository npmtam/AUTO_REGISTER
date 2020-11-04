package Sites;

import com.github.javafaker.Faker;
import commons.AbstractTest;
import commons.Constants;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.VuaKingPageObject;

import java.util.Locale;

public class VuaKing extends AbstractTest {
    private WebDriver driver;
    private Faker faker;
    private VuaKingPageObject vuakingPage;

    @BeforeClass
    public void beforeClass() {
        faker = new Faker(new Locale("en-US"));
    }

    @Parameters({"browser", "url", "invocationCount","sleepAfterTest"})
    @Test
    public void register(String browserName, String url, int invocationCount, int sleetAfterTest) {
        for (int i = 1; i <= invocationCount; i++) {
            driver = getBrowserDriver(browserName);
            vuakingPage = new VuaKingPageObject(driver);

            driver.get("http:\\"+ url);
            vuakingPage.closeAllWindowsWithoutParent();
            String userName = faker.name().firstName() + faker.number().randomNumber();
            String password = faker.name().lastName() + faker.number().randomNumber();

            vuakingPage.inputToUserName(userName);
            vuakingPage.inputToPassword(password);
            vuakingPage.inputToConfirmPassword(password);
            String captcha = vuakingPage.getCaptcha();
            vuakingPage.inputToCaptcha(captcha);
            String currentURL = vuakingPage.getCurrentURL();
            vuakingPage.writeDataToCsv(userName, password, currentURL);
            vuakingPage.clickToRegister();
            vuakingPage.sleepInSecond(sleetAfterTest);
            closeBrowserAndDriver(driver);
        }
    }

}

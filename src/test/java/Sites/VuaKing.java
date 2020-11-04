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
    public void beforeClass(){
        faker = new Faker(new Locale("en-US"));
    }

    @Parameters({"browser", "invocationCount", "sleepAfterTest"})
    @Test
    public void register(String browserName, int invocationCount, int sleetAfterTest){
            driver = getBrowserDriver(browserName);
            vuakingPage = new VuaKingPageObject(driver);

            driver.get(Constants.VUAKING);
            String userName = faker.name().firstName() + faker.number().randomNumber();
            String password = faker.name().lastName() + faker.number().randomNumber();

            vuakingPage.inputToUserName(userName);
            vuakingPage.inputToPassword(password);
            vuakingPage.inputToConfirmPassword(password);
            String captcha = vuakingPage.getCaptcha();
            vuakingPage.inputToCaptcha(captcha);
            String url = vuakingPage.getCurrentURL();
            vuakingPage.writeDataToCsv(userName, password, url);
            vuakingPage.clickToRegister();
            vuakingPage.sleepInSecond(sleetAfterTest);
    }

    @AfterTest
    public void endBrowser(){
        closeBrowserAndDriver(driver);
    }
}

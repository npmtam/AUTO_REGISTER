package Sites;

import com.github.javafaker.Faker;
import commons.AbstractTest;
import commons.Constants;
import org.openqa.selenium.WebDriver;
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

            fakeIP();
            getMyIPAddress();

            log.info("Access URL");
            driver.get("http://"+ url);

            log.info("Vuaking - Close other tabs");
//            vuakingPage.closeAllWindowsWithoutParent();
            String userName = vuakingPage.getFirstNameRandom() + vuakingPage.getLastNameRandom() + vuakingPage.getRandomNumber();
            System.out.println(userName);
            String password = vuakingPage.getLastNameRandom() + vuakingPage.getRandomNumber();

            log.info("Vuaking - Input user name");
            vuakingPage.inputToUserName(userName);

            log.info("Vuaking - Input password and confirm password");
            vuakingPage.inputToPassword(password);
            vuakingPage.inputToConfirmPassword(password);

            log.info("Vuaking - Get captcha");
            String captcha = vuakingPage.getCaptcha();

            log.info("Vuaking - Input captcha");
            vuakingPage.inputToCaptcha(captcha);

            log.info("Vuaking - Get current URL");
            String currentURL = vuakingPage.getCurrentURL();

            log.info("Vuaking - Click to register");
            vuakingPage.clickToRegister();


            log.info("Vuaking - Check the registration successfully");
            verifyTrue(vuakingPage.isLogoDisplayed());

            log.info("Vuaking - Check registration and write data to csv");
            if(Constants.REGISTERED){
                vuakingPage.writeDataToCsv(userName, password, currentURL, Constants.IP_ADDRESS);
                Constants.ACCOUNTS_SUCCESS.add(i);
            }

            vuakingPage.sleepInSecond(sleetAfterTest);
            closeBrowserAndDriver(driver);
        }
    }

}

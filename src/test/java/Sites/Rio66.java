package Sites;

import com.sun.scenario.effect.Crop;
import commons.AbstractTest;
import commons.Constants;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.Rio66PageObject;

public class Rio66 extends AbstractTest {
    private WebDriver driver;
    private Rio66PageObject rioPage;
    String userName, password, apiKey, fullName;

    @BeforeClass
    public void beforeClass() {

    }

    @Parameters({"browser", "url", "invocationCount", "sleepAfterTest"})
    @Test
    public void registerRio66(String browserName, String url, int invocationCount, int sleepAfterTest) {
        for (int i = 1; i <= invocationCount; i++) {
            driver = getBrowserDriver(browserName);
            rioPage = new Rio66PageObject(driver);
            //Install and run TouchVPN to fakeIP
            fakeIP();

            //Access site to get IP address
            getMyIPAddress();

            userName = rioPage.getLastNameRandom() + rioPage.getFirstNameRandom() + rioPage.getRandomNumber();
            password = rioPage.getLastNameRandom() + rioPage.getRandomNumber();
            fullName = rioPage.getFirstNameRandom() + rioPage.getLastNameRandom() + rioPage.getRandomNumber();

            log.info("Access URL");
            driver.get("http://" + url);
            rioPage.sleepInSecond(22);

            log.info("Rio66 - Input username");
            rioPage.inputToUserName(userName);

            log.info("Rio66 - Input password");
            rioPage.inputToPassword(password);

            log.info("Rio66 - Confirm password");
            rioPage.inputToConfirmPassword(password);

            log.info("Rio66 - Solving captcha and get result form recaptcha");
            String captchaSolver = rioPage.solveCaptcha(Constants.API_KEY);
            rioPage.inputToCaptchaTextbox(captchaSolver);

            log.info("Rio66 - Click to register");
            rioPage.clickToRegisterButton();

            log.info("Rio66 - Input to full name");
            rioPage.inputFullNameTextbox(fullName);

            log.info("Rio66 - Click to confirm register");
            rioPage.clickToConfirmRegister();

            String currentURL = rioPage.getCurrentPageURL();

            verifyTrue(rioPage.isTheWelcomeMsgDisplay());
            if (Constants.REGISTERED) {
                log.info("Write data to CSV");
                rioPage.writeDataToCsv(userName, password, currentURL, Constants.IP_ADDRESS);
//            Constants.ACCOUNTS_SUCCESS.add(i);
            }

            rioPage.sleepInSecond(sleepAfterTest);
            closeBrowserAndDriver(driver);
        }
    }

}

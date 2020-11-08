package Sites;

import com.sun.scenario.effect.Crop;
import commons.AbstractTest;
import commons.Constants;
import org.openqa.selenium.WebDriver;
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
        driver = getBrowserDriver("chrome");
        rioPage = new Rio66PageObject(driver);
        //Install and run TouchVPN to fakeIP
        fakeIP();

        //Access site to get IP address
        getMyIPAddress();

        userName = rioPage.getLastNameRandom() + rioPage.getFirstNameRandom() + rioPage.getRandomNumber();
        password = rioPage.getLastNameRandom() + rioPage.getRandomNumber();
        fullName = rioPage.getFirstNameRandom() + rioPage.getLastNameRandom() + rioPage.getRandomNumber();
        apiKey = "b73d2cdf3ba11f3dbdb8b77d4eb06281";
    }

    @Test
    public void registerRio66() {
        driver.get("http://rio66qc.club/");

        rioPage.inputToUserName(userName);
        rioPage.inputToPassword(password);
        rioPage.inputToConfirmPassword(password);

        String captchaSolver = rioPage.solveCaptcha(apiKey);
        rioPage.inputToCaptchaTextbox(captchaSolver);

        rioPage.clickToRegisterButton();

        rioPage.inputFullNameTextbox(fullName);
        rioPage.clickToConfirmRegister();

        String currentURL = rioPage.getCurrentPageURL();

        verifyTrue(rioPage.isTheWelcomeMsgDisplay());
        if(Constants.REGISTERED){
            rioPage.writeDataToCsv(userName, password, currentURL, Constants.IP_ADDRESS);
//            Constants.ACCOUNTS_SUCCESS.add(i);
        }

    }
}

package Sites;

import commons.AbstractTest;
import commons.Constants;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.Tai789PageObject;
import pageUI.Tai789UI;

import java.io.IOException;

public class Tai789 extends AbstractTest {
    private WebDriver driver;
    private Tai789PageObject tai789Page;

    private String userName, password, rePassword;

    @Parameters({"browser", "url", "invocationCount", "sleepAfterTest"})
    @Test
    public void registerTai789(String browserName, String url, int invocationCount, int sleepAfter){
        for(int i=1; i<=invocationCount; i++){
            driver = getBrowserDriver(browserName);
            tai789Page = new Tai789PageObject(driver);

            fakeIP();

            getMyIPAddress();

            userName = tai789Page.getLastNameRandom() + tai789Page.getRandomNumber();
            password = tai789Page.getFirstNameRandom() + tai789Page.getRandomNumber();

            log.info("Access URL");
            driver.get("http://" + url);
            String currentURl = driver.getCurrentUrl();

            log.info("Tai789 - ");
            tai789Page.inputToUserName(userName);

            log.info("Tai789 - Input to password");
            tai789Page.inputToPassword(password);

            log.info("Tai789 - Input to confirm password");
            tai789Page.inputToRePassword(password);

            log.info("Tai789 - Click to register button");
            tai789Page.clickToRegisterButton();

            if(driver.getTitle().equalsIgnoreCase("789 - Game bài số 1 Việt Nam")){
                String afterRegisterURL = driver.getCurrentUrl();
                Constants.REGISTERED = true;
                log.info("Tai789 - Write data to csv");
                try {
                    tai789Page.appendDataToExcel(userName, password, afterRegisterURL, Constants.IP_ADDRESS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Constants.REGISTERED = false;
            }
            verifyTrue(driver.getTitle().equalsIgnoreCase("789 - Game bài số 1 Việt Nam"));
            closeBrowserAndDriver(driver);
        }
    }
}

package Sites;

import commons.AbstractTest;
import commons.Constants;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.B29QCPageObject;
import pageUI.B29QCUI;

import java.io.IOException;

public class B29QC extends AbstractTest {
    private WebDriver driver;
    private B29QCPageObject b29cPage;
    private String userName, password, fullName;

    @Parameters({"browser", "url", "invocationCount", "sleepAfterTest"})
    @Test
    public void registerB29QC(String browserName, String url, int invocationCount, int sleepAfterTest){
        for(int i=1; i<=invocationCount; i++){
            driver = getBrowserDriver(browserName);
            b29cPage = new B29QCPageObject(driver);

            if(browserName.equalsIgnoreCase("chrome")) {
                fakeIP();
                getMyIPAddress();
            }

            userName = b29cPage.getLastNameRandom() + b29cPage.getRandomNumber();
            password = b29cPage.getFirstNameRandom() + b29cPage.getRandomNumber();
            fullName = b29cPage.getFirstNameRandom() + b29cPage.getLastNameRandom() + b29cPage.getRandomNumber();

            log.info("Access URL");
            driver.get("http://" + url);

            log.info("B29QC - Input to username");
            b29cPage.inputToUserName(userName);

            log.info("B29QC - Input to password");
            b29cPage.inputToPassword(password);
            if(b29cPage.isElementPresentInDOM(B29QCUI.PASSWORD_ERROR_MSG)){
                password = b29cPage.getLastNameRandom() + b29cPage.getRandomNumber();
                b29cPage.inputToPassword(password);
            }

            log.info("B29QC - Input to confirm password");
            b29cPage.inputToConfirmPassword(password);

            log.info("B29QC - Get captcha has solved from 2captcha");
            String captcha = b29cPage.solveCaptcha(Constants.API_KEY);

            log.info("B29QC - Input to captcha");
            b29cPage.inputToCaptcha(captcha);

            log.info("B29QC - Click to register");
            b29cPage.clickToRegister();

            String currentURL = driver.getCurrentUrl();

            if(b29cPage.isElementPresentInDOM(B29QCUI.SUCCESS_HTML)){
                Constants.REGISTERED = true;
                log.info("B29QC - Write Data to CSV");
                try {
                    b29cPage.appendDataToExcel(userName, password, currentURL, Constants.IP_ADDRESS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Constants.REGISTERED = false;
            }

            verifyTrue(b29cPage.isElementPresentInDOM(B29QCUI.SUCCESS_HTML));
        }
    }
}
